package vn.nev.tools.pcctool.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import vn.nev.tools.pcctool.common.Constants;
import vn.nev.tools.pcctool.dto.ColumnDto;
import vn.nev.tools.pcctool.dto.ConversionRequestDto;
import vn.nev.tools.pcctool.dto.ConversionResponseDto;
import vn.nev.tools.pcctool.util.NEUtil;

@Service("NEVConversionService")
public class ConversionService extends BaseService implements IConversionService {

  @Autowired
  @Qualifier("codeGenerator")
  private TemplateEngine templateEngine;

  @Override
  public ConversionResponseDto convert(ConversionRequestDto conversion) {
    ConversionResponseDto responseDto = new ConversionResponseDto();

    responseDto.setInBean(generateInBean(conversion));
    responseDto.setOutBean(generateOutBean(conversion));
    responseDto.setDto(generateDto(conversion));
    responseDto.setServiceRemote(generateServiceRemote(conversion));
    responseDto.setService(generateService(conversion));
    responseDto.setDataAccess(generateDataAccess(conversion));

    LOG.info("*******************Converted: " + responseDto);

    return responseDto;
  }

  public void createZipFile(ConversionResponseDto conversion, String outputPath) {

  }

  private String generateInBean(ConversionRequestDto conversion) {

    Map<String, Object> modelData = new HashMap<>();
    modelData.put("serviceId", conversion.getServiceId());
    modelData.put("author", conversion.getAuthor());
    modelData.put("currentDate", NEUtil.formatDate(new Date(), Constants.DateFormat.YYYY_MM_DD));
    modelData.put("fields", conversion.getSelectColumns()
        .stream()
        .map(column -> String.format("%s\r\n", column.getFieldDeclaration()))
        .collect(Collectors.joining("\r\n")));

    modelData.put("getterSetters", conversion.getSelectColumns()
        .stream()
        .map(column -> String.format("%s\r\n\r\n%s",
            column.getGetterString(),
            column.getSetterString())
        ).collect(Collectors.joining("\r\n\r\n")));

    return generateFile("files/bean/inBean", modelData);
  }

  private String generateOutBean(ConversionRequestDto conversion) {
    Map<String, Object> modelData = new HashMap<>();
    modelData.put("serviceId", conversion.getServiceId());
    modelData.put("author", conversion.getAuthor());
    modelData.put("currentDate", NEUtil.formatDate(new Date(), Constants.DateFormat.YYYY_MM_DD));

    String outBeanTemplate = "files/bean/outBean" + conversion.getServiceType().getType();
    return generateFile(outBeanTemplate, modelData);
  }

  private String generateDto(ConversionRequestDto conversion) {
    Map<String, Object> modelData = new HashMap<>();
    modelData.put("dtoName", conversion.getDtoName());
    modelData.put("author", conversion.getAuthor());
    modelData.put("currentDate", NEUtil.formatDate(new Date(), Constants.DateFormat.YYYY_MM_DD));

    List<ColumnDto> dtoColumns = new ArrayList<>();
    dtoColumns.addAll(conversion.getWhereColumns());
    dtoColumns.addAll(conversion.getSelectColumns());

    modelData.put("fields", dtoColumns
        .stream()
        .map(column -> String.format("%s\r\n", column.getFieldDeclaration()))
        .collect(Collectors.joining("\r\n")));

    modelData.put("getterSetters", dtoColumns
        .stream()
        .map(column -> String.format("%s\r\n\r\n%s",
            column.getGetterString(),
            column.getSetterString())
        ).collect(Collectors.joining("\r\n\r\n")));

    return generateFile("files/dto/dto", modelData);
  }

  private String generateService(ConversionRequestDto conversion) {

    Map<String, Object> modelData = new HashMap<>();
    modelData.put("serviceId", conversion.getServiceId());
    modelData.put("dtoName", conversion.getDtoName());
    modelData.put("accessName", conversion.getAccessName());
    modelData.put("author", conversion.getAuthor());
    modelData.put("currentDate", NEUtil.formatDate(new Date(), Constants.DateFormat.YYYY_MM_DD));

    String copyPropertiesString = conversion.getWhereColumns()
        .stream()
        .map(column -> column.getCopyString("inBean", "inDto"))
        .collect(Collectors.joining(Constants.LINE_SEPARATOR_WINDOWS));
    modelData.put("copyPropertiesString", copyPropertiesString);

    String serviceTemplate = "files/service/service" + conversion.getServiceType().getType();

    return generateFile(serviceTemplate, modelData);

  }

  private String generateServiceRemote(ConversionRequestDto conversion) {
    Map<String, Object> modelData = new HashMap<>();
    modelData.put("serviceId", conversion.getServiceId());
    modelData.put("author", conversion.getAuthor());
    modelData.put("currentDate", NEUtil.formatDate(new Date(), Constants.DateFormat.YYYY_MM_DD));

    return generateFile("files/service/serviceRemote", modelData);
  }


  private String generateDataAccess(ConversionRequestDto conversion) {

    Map<String, Object> modelData = new HashMap<>();
    modelData.put("serviceId", conversion.getServiceId());
    modelData.put("dtoName", conversion.getDtoName());
    modelData.put("accessName", conversion.getAccessName());
    modelData.put("author", conversion.getAuthor());
    modelData.put("currentDate", NEUtil.formatDate(new Date(), Constants.DateFormat.YYYY_MM_DD));

    switch (conversion.getServiceType()) {
      case SELECT:
        return generateRAccess(conversion, modelData);

      case STORE:
        return generatePAccess(conversion, modelData);

      default:
        return Constants.EMPTY;
    }
  }

  private String generateRAccess(ConversionRequestDto conversion, Map<String, Object> modelData) {
    String setParamDtoString = conversion.getWhereColumns()
        .stream()
        .map(column -> String
            .format("    whereList.add(whereDto.get%s());", column.getColumnNamePascal()))
        .collect(Collectors.joining(Constants.LINE_SEPARATOR_WINDOWS));
    modelData.put("setParamDtoString", setParamDtoString);

    String copyDbToRetDtoString = conversion.getSelectColumns()
        .stream()
        .map(column -> String
            .format("        retDto.set%s(rs.getString(\"%s\"));", column.getColumnNamePascal(),
                column.getColumnName()))
        .collect(Collectors.joining(Constants.LINE_SEPARATOR_WINDOWS));
    modelData.put("copyDbToRetDtoString", copyDbToRetDtoString);

    String templateName = "files/access/accessR";

    return generateFile(templateName, modelData);
  }

  private String generatePAccess(ConversionRequestDto conversion, Map<String, Object> modelData) {
    modelData.put("outParamIndex", conversion.getSelectColumns().size() + 1);

    String registerInParametersString = conversion.getWhereColumns()
        .stream()
        .map(column -> String
            .format("      cstmt.setString(i++, inDto.get%s());", column.getColumnNamePascal()))
        .collect(Collectors.joining(Constants.LINE_SEPARATOR_WINDOWS));
    modelData.put("registerInParametersString", registerInParametersString);

    String registerOutParametersString = conversion.getSelectColumns()
        .stream()
        .map(column -> "      cstmt.registerOutParameter(i++, Types.VARCHAR);")
        .collect(Collectors.joining(Constants.LINE_SEPARATOR_WINDOWS));
    modelData.put("registerOutParametersString", registerOutParametersString);

    String copyDbToRetDtoString = conversion.getSelectColumns()
        .stream()
        .map(column -> String
            .format("        retDto.set%s(cstmt.getString(i++));", column.getColumnNamePascal()))
        .collect(Collectors.joining(Constants.LINE_SEPARATOR_WINDOWS));
    modelData.put("copyDbToRetDtoString", copyDbToRetDtoString);

    String templateName = "files/access/accessP";

    return generateFile(templateName, modelData);
  }

  /**
   * Sinh source code từ file template và model data
   */
  private String generateFile(String templateName, Map<String, Object> modelData) {
    Context context = new Context(Locale.getDefault());
    modelData.forEach(context::setVariable);
    String content = templateEngine.process(templateName, context);

    return content;
  }
}
