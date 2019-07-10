package vn.nev.tools.pcctool.service;

import org.springframework.stereotype.Service;
import vn.nev.tools.pcctool.dto.ConvertionRequestDto;
import vn.nev.tools.pcctool.dto.ConvertionResponseDto;

@Service
public class ConvertionService extends BaseService implements IConvertionService {

  @Override
  public ConvertionResponseDto convert(ConvertionRequestDto convertion) {
    LOG.info("*******************Converting: " + convertion.getQuery());

    ConvertionResponseDto responseDto = new ConvertionResponseDto();
    responseDto.setConvertedQuery(
        convertion.getQuery()
            .replaceAll("^\\/\\/.*$", "")
            .replaceAll("\'(.*)\'", "$1")
    );

    LOG.info("*******************Converted: " + responseDto.getConvertedQuery());

    return responseDto;
  }
}
