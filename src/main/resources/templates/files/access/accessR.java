package jp.co.nissho_ele.acc.acj.entity.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jp.co.nissho_ele.acc.acj.entity.dto.[(${dtoName})];
import jp.co.nissho_ele.acc.base_package.common.util.ACCConvertUtil;
import jp.co.nissho_ele.acc.base_package.entity.access.AccessBase;

/**
 * [(${accessName})].java
 * --------------------------------------------------------------------------------------------*
 * History<br/>
 * Version YYYY/MM/DD Author       Content
 * --------------------------------------------------------------------------------------------*
 * 1.0     [(${currentDate})] NEV-[( ${author} )]   Create
 * --------------------------------------------------------------------------------------------*
 */
public class [(${accessName})] extends AccessBase {

  /** Sql statement. */
  private static final String SELECT1_SQL = getSelect1Sql();

  /**
   * .
   *
   * @param conn
   *          DBコネクション
   * @param whereDto
   *          [(${serviceId})]検索引数
   * @return 取得結果
   * @throws Exception
   *           Exception
   */
  public final List<[(${dtoName})]> select1(final Connection conn,
      final [(${dtoName})] whereDto) throws Exception {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<[(${dtoName})]> retDataArray = null;
    List<String> whereList = this.setParamDto(whereDto);

    try {

      pstmt = conn.prepareStatement(SELECT1_SQL);

      // Set paramDto into sql1 statement.
      for (int i = 0; i < whereList.size(); i++) {
        pstmt.setString((i + 1), whereList.get(i));
      }

      // SQL実行
      rs = pstmt.executeQuery();

      /* 結果の取得 */
      // DTOクラスの生成
      retDataArray = new ArrayList<>();
      [(${dtoName})] retDto = null;
      while (rs.next()) {
        retDto = new [(${dtoName})]();

        // 取得値の設定
[(${copyDbToRetDtoString})]

        retDataArray.add(retDto);
      }

    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
      } finally {
        if (pstmt != null) {
          pstmt.close();
        }
      }
    }

    return retDataArray;
  }

  /**
   * Set parameterDto.
   *
   * @param whereDto
   * @return whereList paramDto.
   */
  private List<String> setParamDto([(${dtoName})] whereDto) {
    List<String> whereList = new ArrayList<>();

[(${setParamDtoString})]

    return whereList;
  }

  /**
   * @return sql1 statement.
   */
  private static String getSelect1Sql() {

    StringBuilder sql = new StringBuilder();
// TODO: copy SQL to here
//    sql.append("SELECT ");
//    sql.append("   XXX ");
//    sql.append("FROM ");
//    sql.append("   YYY ");
//    sql.append("WHERE ");
//    sql.append("   ZZZ = ? ");

    return sql.toString();
  }

}
