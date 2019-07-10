package vn.nev.tools.pcctool.service;

import vn.nev.tools.pcctool.dto.ConvertionRequestDto;
import vn.nev.tools.pcctool.dto.ConvertionResponseDto;

public interface IConvertionService {

  ConvertionResponseDto convert(ConvertionRequestDto convertion);
}
