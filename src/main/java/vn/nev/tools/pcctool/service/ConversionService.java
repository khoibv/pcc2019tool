package vn.nev.tools.pcctool.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import vn.nev.tools.pcctool.common.Constants;
import vn.nev.tools.pcctool.common.exception.NEIOException;
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

    responseDto.setServiceId(conversion.getServiceId());
    responseDto.setDtoName(conversion.getDtoName());
    responseDto.setAccessName(conversion.getAccessName());

    responseDto.setInBean(generateInBean(conversion));
    responseDto.setOutBean(generateOutBean(conversion));
    responseDto.setDto(generateDto(conversion));
    responseDto.setServiceRemote(generateServiceRemote(conversion));
    responseDto.setService(generateService(conversion));
    responseDto.setDataAccess(generateDataAccess(conversion));

    return responseDto;
  }

  @Override
  public void createZipFile(final ConversionResponseDto conversion, String outputFile) {

    final String path = "acc_ejb/ejbModule/jp/co/nissho_ele/acc/acj";
    File file = new File(outputFile);
    String serviceId = conversion.getServiceId();

    Map<String, String> map = new HashMap<>();
    map.put("/bean/" + serviceId + "InBean.java", conversion.getInBean());
    map.put("/bean/" + serviceId + "OutBean.java", conversion.getOutBean());
    map.put("/entity/access/" + conversion.getAccessName() + ".java", conversion.getDataAccess());
    map.put("/entity/dto/" + conversion.getDtoName() + ".java", conversion.getDto());
    map.put("/service/" + serviceId + "Service.java", conversion.getService());
    map.put("/service/" + serviceId + "ServiceRemote.java", conversion.getServiceRemote());

    try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file))) {
      for (Entry<String, String> entry : map.entrySet()) {
        String filePath = path + entry.getKey();
        String content = entry.getValue();

        ZipEntry zipFile = new ZipEntry(filePath);
        out.putNextEntry(zipFile);
        byte[] data = content.getBytes();
        out.write(data, 0, data.length);
        out.closeEntry();
      }
    } catch (IOException ioe) {
      throw new NEIOException(ioe);
    }
  }

  private String generateInBean(ConversionRequestDto conversion) {

    Map<String, Object> modelData = new HashMap<>();
    modelData.put("serviceId", conversion.getServiceId());
    modelData.put("author", conversion.getAuthor());
    modelData.put("currentDate", NEUtil.formatDate(new Date(), Constants.DateFormat.YYYY_MM_DD));
    modelData.put("fields", conversion.getWhereColumns()
        .stream()
        .map(ColumnDto::getFieldDeclaration)
        .collect(Collectors.joining(Constants.LINE_SEPARATOR_WINDOWS2)));

    modelData.put("getterSetters", conversion.getWhereColumns()
        .stream()
        .map(ColumnDto::getGetterSetterString)
        .collect(Collectors.joining(Constants.LINE_SEPARATOR_WINDOWS2)));

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
    dtoColumns.addAll(conversion.getSelectColumns()
        .stream()
        .filter(colSelect -> conversion.getWhereColumns()
            .stream()
            // Tránh add các column trùng lặp trong cả IN và OUT
            .noneMatch(colWhere -> colWhere.getColumnNamePascal()
                .equals(colSelect.getColumnNamePascal())))
        .collect(Collectors.toList())
    );

    modelData.put("fields", dtoColumns
        .stream()
        .map(ColumnDto::getFieldDeclaration)
        .collect(Collectors.joining(Constants.LINE_SEPARATOR_WINDOWS2)));

    modelData.put("getterSetters", dtoColumns
        .stream()
        .map(ColumnDto::getGetterSetterString)
        .collect(Collectors.joining(Constants.LINE_SEPARATOR_WINDOWS2)));

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
        .map(column -> String.format("    whereList.add(whereDto.get%s());",
            column.getColumnNamePascal()))
        .collect(Collectors.joining(Constants.LINE_SEPARATOR_WINDOWS));
    modelData.put("setParamDtoString", setParamDtoString);

    String copyDbToRetDtoString = conversion.getSelectColumns()
        .stream()
        .map(column -> String.format("        retDto.set%s(rs.getString(\"%s\"));",
            column.getColumnNamePascal(), column.getColumnName()))
        .collect(Collectors.joining(Constants.LINE_SEPARATOR_WINDOWS));
    modelData.put("copyDbToRetDtoString", copyDbToRetDtoString);

    String templateName = "files/access/accessR";

    return generateFile(templateName, modelData);
  }

  private String generatePAccess(ConversionRequestDto conversion, Map<String, Object> modelData) {
    modelData.put("outParamIndex", conversion.getSelectColumns().size() + 1);

    String registerInParametersString = conversion.getWhereColumns()
        .stream()
        .map(column -> String.format("      cstmt.setString(i++, inDto.get%s());",
            column.getColumnNamePascal()))
        .collect(Collectors.joining(Constants.LINE_SEPARATOR_WINDOWS));
    modelData.put("registerInParametersString", registerInParametersString);

    String registerOutParametersString = conversion.getSelectColumns()
        .stream()
        .map(column -> "      cstmt.registerOutParameter(i++, Types.VARCHAR);")
        .collect(Collectors.joining(Constants.LINE_SEPARATOR_WINDOWS));
    modelData.put("registerOutParametersString", registerOutParametersString);

    String copyDbToRetDtoString = conversion.getSelectColumns()
        .stream()
        .map(column -> String.format("        retDto.set%s(cstmt.getString(i++));",
            column.getColumnNamePascal()))
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

    return templateEngine.process(templateName, context);
  }
}
