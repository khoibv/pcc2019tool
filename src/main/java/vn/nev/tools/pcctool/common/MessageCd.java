package vn.nev.tools.pcctool.common;

public final class MessageCd {

  /*********************/
    /* SYSTEM ERROR
    /*********************/
  /**
   * System error occurs. Please contact with administrator<br/> Có lỗi hệ thống. Xin hãy liên lạc
   * với người quản trị
   */
  public static final String E0001 = "E0001";

  /**
   * Register failed<br/> Đăng ký thất bại
   */
  public static final String E0002 = "E0002";

  /**
   * Update information is not found<br/> Không tìm thấy thông tin update
   */
  public static final String E0003 = "E0003";

  /**
   * Update failed<br/> Update thất bại
   */
  public static final String E0004 = "E0004";

  /**
   * Delete information is not found<br/> Không tìm thấy thông tin xóa
   */
  public static final String E0005 = "E0005";

  /**
   * Delete failed<br/> Xóa thất bại
   */
  public static final String E0006 = "E0006";

  /**
   * {0} is required<br/> {0} bắt buộc nhập
   */
  public static final String E0007 = "E0007";

  /**
   * {0}: length must be less than or equals to {1}<br/> Kích thước của {0} phải nhỏ hơn hoặc bằng
   * {1}
   */
  public static final String E0008 = "E0008";

  /**
   * {0} must be less than or equals {1}
   */
  public static final String E0009 = "E0009";

  /**
   * Please select at least 1 record
   */
  public static final String E0010 = "E0010";

  /**
   * must contain alphabetic characters or numbers only
   */
  public static final String E0011 = "E0011";

  /**
   * Email is invalid or not exists
   */
  public static final String E0012 = "E0012";

  /**
   * Password and new password must be different
   */
  public static final String E0013 = "E0013";

  /**
   * Password is incorrect
   */
  public static final String E0014 = "E0014";

  /**
   * must contain number
   */
  public static final String E0015 = "E0015";

  /**
   * There are not price list for this customer. Please create price list first
   */
  public static final String E0016 = "E0016";

  /**
   * There are not price list for this type/level. Please create price list first
   */
  public static final String E0017 = "E0017";

  /**
   * Invoice is created. Can not create again
   */
  public static final String E0018 = "E0018";

  /*********************/
    /* ACCOUNT ERROR
    /*********************/
  /**
   * User ID is duplicated<br/> Tên đăng nhập đã tồn tại trong hệ thống
   */
  public static final String E0100 = "E0100";

  /**
   * Email is duplicated<br/> Email đã tồn tại trong hệ thống
   */
  public static final String E0101 = "E0101";

  /**
   * User ID or password is incorrect<br/> Tên đăng nhập hoặc email không đúng
   */
  public static final String E0102 = "E0102";

  /**
   * Passwords not match<br/> Password và password xác nhận phải khớp nhau
   */
  public static final String E0103 = "E0103";

  /**
   * There are 2 rows with same Type and Level
   */
  public static final String E0110 = "E0110";

  /**
   * There are 2 rows with same Customer, Type and Level
   */
  public static final String E0111 = "E0111";

  /**
   * Total product of child job not equal product of main job
   */
  public static final String E0200 = "E0200";

  /**
   * Do not split to one child job
   */
  public static final String E0201 = "E0201";

  /*********************/
    /* INFORMATION
    /*********************/
  /**
   * Register succesful<br/> Đăng ký thành công
   */
  public static final String I0001 = "I0001";

  /**
   * Update succesful<br/> Update thành công
   */
  public static final String I0002 = "I0002";

  /**
   * Delete succesful<br/> Xóa thành công
   */
  public static final String I0003 = "I0003";

  /**
   * <pre>
   * {0} assigned to {1} a task {2}
   *    {0}: Leader name
   *    {1}: Editor name
   *    {2}: Task name
   * </pre>
   */
  public static final String I0004 = "I0004";

  /**
   * <pre>
   * {0} complete task {1}
   *    {0}: Editor name
   *    {1}: Task name
   * </pre>
   */
  public static final String I0005 = "I0005";

  /**
   * <pre>
   * {0} completed review task {1}
   *    {0}: Reviewer name
   *    {1}: Task name
   * </pre>
   */
  public static final String I0006 = "I0006";

  /**
   * <pre>
   * {0} commented on task {1}: {2}
   *    {0}: User who comment
   *    {1}: Task name
   *    {2}: Comment content
   * </pre>
   */
  public static final String I0007 = "I0007";

  /*********************/
    /* CONFIRM
    /*********************/
  /**
   * Do you really want to delete?
   */
  public static final String C0001 = "C0001";

  /**
   * There are different customers. Do you really want to create invoice for different customers?
   */
  public static final String C0002 = "C0002";

    /**
     * hour >=0 and <=8
     */
    public static final String E0301 = "E0301";
    /**
     * Total hour >=0 and <=24
     */
    public static final String E0302 = "E0302";

  /**
   * check block 0.5h
   */
  public static final String E0303 = "E0303";

  /**
   * format date yyyy-MM
   */
  public static final String E0304 = "E0304";
}
