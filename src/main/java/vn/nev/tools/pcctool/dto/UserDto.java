package vn.nev.tools.pcctool.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto implements Serializable {

  private static final long serialVersionUID = 734365909633088086L;
  private Long id;

//  @NotEmpty(groups = {Default.class, Profile.class})
//  @Size(max = ColumnLengths.USER_FULL_NAME, groups = {Default.class, Profile.class})
  private String fullName;

  @NotEmpty
//  @Size(max = ColumnLengths.EMAIL)
//  @Email(regexp = Constants.Patterns.EMAIL, message = "{" + MessageCd.E0012 + "}")
  private String email;

  @NotEmpty
//  @Size(max = ColumnLengths.USER_NAME)
//  @Pattern(regexp = Patterns.PATTERN_ALPHA_NUMBER, message = "{" + MessageCd.E0011 + "}")
  private String userName;

  @JsonIgnore
  private String password;

//  @Size(max = ColumnLengths.PHOTO_URL, groups = {Default.class, Profile.class})
  private String photoUrl;

//  private UserStatus userStatus;

  @NotNull
  private Long departmentId;
  private String departmentName;

  private String phone;

//  @Size(max = ColumnLengths.EMP_POSITION)
  private String position;

//  @Size(max = ColumnLengths.USER_CODE)
  private String code;

//  private Sex sex;

  private Date dateOfBirth;

//  @JsonIgnore
//  private Set<AuthorityDto> authorities = new HashSet<>();

//  @NotEmpty
//  private List<Long> authorityIds = new ArrayList<>();

//  private int baseSalary;

}
