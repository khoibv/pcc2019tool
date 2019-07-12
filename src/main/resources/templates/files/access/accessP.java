package jp.co.nissho_ele.acc.acj.entity.access;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import jp.co.nissho_ele.acc.acj.entity.dto.[(${dtoName})];
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

  /** 入力用パラメータの開始インデックス. */
  private static final int IN_PARAM_START_IDX = 1;

  /** 出力用パラメータの開始インデックス(proc1). */
  private static final int OUT_PARAM_START_IDX_PROC1 = [(${outParamIndex})];

  /** Sql statement. */
  private static final String SQL_PROC1 = getProc1Sql();

  /**
   * .
   *
   * @param conn
   *          DBコネクション
   * @param inDto
   *          DTO
   * @return 更新結果
   * @throws Exception
   *           Exception
   */
  public final [(${dtoName})] proc1(final Connection conn, final [(${dtoName})] inDto)
      throws Exception {
    CallableStatement cstmt = null;
    [(${dtoName})] retDto = null;

    try {
      // SQLコンテナ作成
      cstmt = conn.prepareCall(SQL_PROC1);

      /* 値の設定 */
      int i = IN_PARAM_START_IDX;

      // 条件値の設定
[(${registerInParametersString})]

      // 取得値の設定
[(${registerOutParametersString})]

      // SQL実行
      cstmt.executeUpdate();

      /* 結果の取得 */
      // DTOクラスの生成
      retDto = new [(${dtoName})]();

      // 取得値の設定
      i = OUT_PARAM_START_IDX_PROC1;

[(${copyDbToRetDtoString})]

      if (FAILED.equals(retDto.getRetCode())) {
        throw createAccDAException(retDto.getMsg(), null, null, null, null, null, null);
      }

    } finally {
      if (cstmt != null) {
        try {
          cstmt.close();
        } finally {
          cstmt = null;
        }
      }
    }

    return retDto;
  }

  /**
   * Get query string (proc1).
   *
   * @return store proceduce statement.
   */
  private static String getProc1Sql() {
    StringBuilder sql = new StringBuilder();
// TODO: copy SQL to here
//    sql.append("{CALL JPRC_XXXX(");
//    sql.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
//    sql.append("?, ?, ?, ?, ?, ?");
//    sql.append(")}");

    return sql.toString();
  }
}
