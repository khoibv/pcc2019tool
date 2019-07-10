package vn.nev.tools.pcctool.common.config;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.stp.common.MessageCd;
import vn.stp.common.exception.StpSalaryException;
import vn.stp.dto.ResponseDto;
import vn.stp.util.ResourceUtil;

@RestControllerAdvice
public class StpSalaryControllerAdvice extends ResponseEntityExceptionHandler {

  @Autowired
  private ResourceUtil resourceUtil;

  @ExceptionHandler
  @ResponseBody
  public ResponseEntity<ResponseDto> handleControllerException(HttpServletRequest request, Throwable ex) {
    logger.error("Error", ex);

    if (isAjax(request)) {

      HttpStatus status = getStatus(request);
      return new ResponseEntity<>(ResponseDto.error(MessageCd.E0001, resourceUtil.getMessage(MessageCd.E0001)), status);
    } else {
      throw new StpSalaryException(ex);
    }
  }

  private HttpStatus getStatus(HttpServletRequest request) {
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    if (statusCode == null) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return HttpStatus.valueOf(statusCode);
  }

  private boolean isAjax(HttpServletRequest request) {
    String requestedWithHeader = request.getHeader("X-Requested-With");
    return "XMLHttpRequest".equals(requestedWithHeader);
  }

}
