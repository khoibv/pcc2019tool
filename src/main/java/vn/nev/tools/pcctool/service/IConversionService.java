package vn.nev.tools.pcctool.service;

import vn.nev.tools.pcctool.dto.ConversionRequestDto;
import vn.nev.tools.pcctool.dto.ConversionResponseDto;

public interface IConversionService {

  ConversionResponseDto convert(ConversionRequestDto conversion);

  void createZipFile(ConversionResponseDto conversion, String outputFile);
}
