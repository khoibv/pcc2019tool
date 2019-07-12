package vn.nev.tools.pcctool.common;

public class Constants {


  public static final String EMPTY = "";
  public static final String DOT = ".";
  public static final String COMMA = ",";
  public static final String STRING_JOIN_COMMA = ", ";

  public static final String LINE_SEPARATOR = System.lineSeparator();
  public static final String LINE_SEPARATOR_WINDOWS = "\r\n";

  public static final int RANGE_FILTER_DAY = -25;

  public static final String TASK_CONVERSATION_CODE_FORMAT = "TASK-{0}";

  /**
   * String format: {ProductTypeName} - {ProductLevelName}
   */
  public static final String TASK_DETAIL_NAME_FORMAT = "{0} - {1}";

  public static final String APPLICATION_EXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

  /**
   * URL
   */
  public final class Url {

    public static final String HOME = "/";
    public static final String LOGIN = "/login";
    public static final String LOGIN_FAILED = "/login?error=true";
    public static final String LOGOUT = "/logout";

    private Url() {
    }
  }

  /**
   * Date formats
   */
  public final class DateFormat {

    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDD_HHMM = "yyyyMMdd_HHmm";
    public static final String YYYYMMDD_HHMMSS = "yyyyMMdd_HHmmss";
    public static final String YYYY_MM_DD = "yyyy/MM/dd";
    public static final String MM_YYYY = "MM/yyyy";
    public static final String YYYY_MM_DD_T_HH_MM_SS_SSS = "yyyy/MM/dd'T'HH:mm:ss.SSS";

    /**
     * November 7, 2018, 12:25 PM
     */
    public static final String MMMM_D_YYYY_H_M_A = "MMMM d, YYYY, h:m a";

    private DateFormat() {
    }
  }

  /**
   * Session keys
   */
  public final class Session {

    public static final String CURRENT_USER = "CURRENT_USER";
    public static final String DOWNLOAD_FILE = "DOWNLOAD_FILE";

    private Session() {
    }
  }

  /**
   * View location
   */
  public final class Views {

    public static final String HOME = "home/index";
    public static final String LOGIN = "home/login";
    public static final String RESET_PASSWORD = "home/resetPassword";
    public static final String CHANGE_PASSWORD = "home/changePassword";
    public static final String SIGNUP = "account/signup";

    private Views() {
    }
  }

  /**
   * Regular expression patterns
   */
  public final class Patterns {

    public static final String EMAIL = "^[_A-Za-z0-9-+](.[_A-Za-z0-9-]+)*@" +
        "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    public static final String PATTERN_ALPHA_NUMBER = "^[A-Za-z0-9]*$";

    public static final String PATTERN_ALPHA_NUMBER_EXT = "^[A-Za-z0-9._]*$";

    public static final String PATTERN_ALPHA_NUMBER_WITH_SPACE = "^[A-Za-z0-9\\s++]*$";

    public static final String PATTERN_ALPHA = "^[A-Za-z]*$";

    public static final String PATTERN_NUMBER = "^[0-9]*$";

    public static final String PATTERN_FULL_NUMBER = "^$|^-?[0-9]\\d*(\\.\\d+)?$";

    public static final String YYYY_MM_DD = "yyyy/MM/dd";
    public static final String YYYY_MM = "yyyy-MM";

    private Patterns() {
    }
  }

  public final class WebSocket {


    private WebSocket() {
    }
  }
}
