package vn.nev.tools.pcctool.dto;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import vn.nev.tools.pcctool.dto.View.BaseView;

public class ResponseDto<T> {

  /**
   * Status of processing <br/>
   * <code>true</code>: OK, <code>false</code>: NG
   */
  @JsonView(BaseView.class)
  private boolean success;

  /**
   * Error message in the case of processing have any errors
   */
  @JsonView(BaseView.class)
  private List<MessageDto> messages;

  /**
   * Output data in the case of processing is successful
   */
  @JsonView(BaseView.class)
  private T response;

  public ResponseDto() {
  }

  public ResponseDto(boolean success, List<MessageDto> messages, T response) {
    this.success = success;
    this.messages = messages;
    this.response = response;
  }

  public ResponseDto(boolean success) {
    this(success, null, null);
  }

  /**
   * Constructor for failing processing
   *
   * @param messages Error messages
   */
  public ResponseDto(List<MessageDto> messages) {
    this(false, messages, null);
  }

  /**
   * Constructor for failing processing
   *
   * @param message Error message
   */
  public ResponseDto(MessageDto message) {
    this(false, Arrays.asList(message), null);
  }

  public ResponseDto(boolean success, T response) {
    this(success, null, response);
  }

  /**
   * Constructor for failing processing
   *
   * @param bindingResult Error
   */
  public ResponseDto(BindingResult bindingResult) {
    // TODO: Extract error message from BindingResult
    List<MessageDto> errorMessages = new ArrayList<>();

    this.success = false;
    this.messages = errorMessages;
    this.response = null;
  }


  public static <T> ResponseDto<T> success(T object) {
    return new ResponseDto(true, null, object);
  }

  public static <T> ResponseDto<T> success() {
    return new ResponseDto(true, null, (T) null);
  }

  public static <T> ResponseDto<T> error(String messageCd, String message) {
    return new ResponseDto(false, Arrays.asList(new MessageDto(messageCd, message)), null);
  }

  public static <T> ResponseDto<T> error(Errors errors) {
    List<MessageDto> messages = errors.getFieldErrors()
        .stream()
        .map(e -> new MessageDto(e.getField(), e.getDefaultMessage()))
        .collect(Collectors.toList());

    return new ResponseDto(false, messages, null);
  }

  @Override
  public String toString() {
    return "ResponseDto{" +
        "success=" + success +
        ", messages=" + messages +
        ", response=" + response +
        "}";
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public List<MessageDto> getMessages() {
    return messages;
  }

  public void setMessages(List<MessageDto> messages) {
    this.messages = messages;
  }

  public T getResponse() {
    return response;
  }

  public void setResponse(T response) {
    this.response = response;
  }
}
