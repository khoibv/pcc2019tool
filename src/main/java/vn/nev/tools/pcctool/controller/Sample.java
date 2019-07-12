package vn.nev.tools.pcctool.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import vn.nev.tools.pcctool.util.NEUtil;

public class Sample {


  public static void main(String[] args) throws IOException {
//    test01();

//    System.out.println(NEUtil.toPascalCase("SAMPLE_COLUMN_NAME"));
//
//    String msg = MessageFormat.format("public String set{0}(String {1}) '{' this.{1} = {1}; '}'", "Name", "name");
//    System.out.println(msg);

    test02();
  }

  private static void test02() throws IOException {

    List<String> args = Arrays.asList("Sample", "Zip");

    String outputFile = "/Users/khoibv/tmp/new.zip";
    StringBuilder sb = new StringBuilder();
    sb.append("Test String");

    File f = new File(outputFile);
    ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
    ZipEntry e = new ZipEntry("mytext.txt");
    out.putNextEntry(e);

    byte[] data = sb.toString().getBytes();
    out.write(data, 0, data.length);
    out.closeEntry();

    ZipEntry e2 = new ZipEntry("child/text2.txt");
    out.putNextEntry(e2);

    byte[] data2 = "Another string".getBytes();
    out.write(data2, 0, data2.length);
    out.closeEntry();

    out.close();
  }

  private static void test01() {
    String source = "conSCH_NEXT          = 'SELECT ABC,' +\n"
        + "                           ' TERM_CODE,' +\n"
        + "                           ' TERM_DATE,' +\n"
        + "                           ' NEXT_CODE,' +\n"
        + "                           ' NEXT_DATE,' +\n"
        + "                           ' LIMIT_DATE,' +\n"
        + "                           ' TIMESTAMP,' +\n"
        + "                           ' OPE_CODE,' +\n"
        + "                           ' COMPANY_CODE,' +\n"
        + "                           ' DEPT_NO,' +\n"
        + "                           ' CUSTOMER_NO,' +\n"
        + "                           ' WORK_CODE,' +\n"
        + "                           ' STATE_KIND_CODE,' +\n"
        + "                           ' STATE_KIND_SUB_CODE,' +\n"
        + "                           ' STATE_CODE,' +\n"
        + "                           ' GUARANTEE_KUBUN_CODE,' +\n"
        + "                           ' GUARANTEE_CODE,' +\n"
        + "                           ' INPUT_COUNT,' +\n"
        + "                           ' INPUT_KUBUN_CODE,' +\n"
        + "                           ' INPUT_STATEMENT,' +\n"
        + "                           ' INPUT_MONEY,' +\n"
        + "                           ' INPUT_DATE,' +\n"
        + "                           ' INPUT_DUTY,' +\n"
        + "                           ' INPUT_COURTROOM,' +\n"
        + "                           ' INPUT_HOUSE,' +\n"
        + "                           ' INPUT_OPE_PERSON,' +\n"
        + "                           ' COURT_CODE,' +\n"
        + "//[MOD]2019/02/19 元号対応 K.tsuboyama START\n"
        + "                           //' JIKEN_NO1,' +\n"
        + "                           ' CASE NEWERA_FLG WHEN ''0'' THEN JIKEN_NO1 WHEN ''1'' THEN TO_CHAR(TO_NUMBER(JIKEN_NO1)-30) END AS JIKEN_NO1 ,'+\n"
        + "//[MOD]2019/02/19 元号対応 K.tsuboyama END\n"
        + "                           ' JIKEN_NO2,' +\n"
        + "                           ' JIKEN_NO3,' +\n"
        + "//[MOD]2019/02/19 元号対応 K.tsuboyama START\n"
        + "                           //' JIKEN2_NO1,' +\n"
        + "                           ' CASE NEWERA_FLG2 WHEN ''0'' THEN JIKEN2_NO1 WHEN ''1'' THEN TO_CHAR(TO_NUMBER(JIKEN2_NO1)-30) END AS JIKEN2_NO1 ,'+\n"
        + "//[MOD]2019/02/19 元号対応 K.tsuboyama END\n"
        + "                           ' JIKEN2_NO2,' +\n"
        + "                           ' JIKEN2_NO3,' +\n"
        + "                           ' NOW_KANA_NAME,' +\n"
        + "                           ' NOW_KANJI_NAME,' +\n"
        + "                           ' NOW_TERM_NAME,' +\n"
        + "                           ' NEXT_TERM_NAME,' +\n"
        + "                           ' OPERATOR_NAME,' +\n"
        + "                           ' TEAM_CODE,' +\n"
        + "                           ' COURT_KUBUN_CODE,' +\n"
        + "                           ' COURT_NAME,' +\n"
        + "                           ' COURT_BRANCH_NAME,' +\n"
        + "                           ' IKAN_NOW_GYOUMU_STATUS_NAME,' +\n"
        + "                           ' IKAN_NOW_GYOUMU_STATUS,' +\n"
        + "                           ' IKAN_GYOUMU_STATUS,' +\n"
        + "                           ' CHARGE_POSITION_CODE,' +\n"
        + "                           ' NEW_GRADE' +\n"
        + "                           ' ,NEWERA_FLG,' +   //[ADD]2019/02/19 元号対応 K.tsuboyama\n"
        + "                           ' NEWERA_FLG2' +   //[ADD]2019/02/19 元号対応 K.tsuboyama\n"
        + "                           ' FROM' +\n"
        + "                           '  JVIEW_SCHEDULE' +\n"
        + "                           ' WHERE' +\n"
        + "// 2016/02/18 No86 Egashira Mod Start\n"
        + "//                           '  ( ' +\n"
        + "//                           '    (TRUNC(TERM_DATE) = :WHERE_TERM_DATE AND TERM_CODE IN (2070, 2080))' +\n"
        + "//                           '    OR ' +\n"
        + "//                           '    (TRUNC(NEXT_DATE) = :WHERE_TERM_DATE)' +\n"
        + "//                           '    OR ' +\n"
        + "//                           '    ( ' +\n"
        + "//                           '      (TRUNC(IKAN_KAINYU_SIJI_UPDATE_DATE) = :WHERE_TERM_DATE ' +\n"
        + "//                           '        AND IKAN_KAINYU_GYOUMU_STATUS IN (''1'')) ' +\n"
        + "//                           '      OR' +\n"
        + "//                           '      (TRUNC(IKAN_KAIJI_SIJI_UPDATE_DATE) = :WHERE_TERM_DATE ' +\n"
        + "//                           '        AND IKAN_KAIJI_GYOUMU_STATUS IN (''1'')) ' +\n"
        + "//                           '      OR' +\n"
        + "//                           '      (TRUNC(IKAN_KOBETU_SIJI_UPDATE_DATE) = :WHERE_TERM_DATE ' +\n"
        + "//                           '        AND IKAN_KOBETU_GYOUMU_STATUS IN (''1'')) ' +\n"
        + "//                           '      OR' +\n"
        + "//                           '      (TRUNC(IKAN_SONOTA_SIJI_UPDATE_DATE) = :WHERE_TERM_DATE ' +\n"
        + "//                           '        AND IKAN_SONOTA_GYOUMU_STATUS IN (''1'')) ' +\n"
        + "//                           '      OR' +\n"
        + "//                           '      (TRUNC(IKAN_TODOKE_SIJI_UPDATE_DATE) = :WHERE_TERM_DATE ' +\n"
        + "//                           '        AND IKAN_TODOKE_GYOUMU_STATUS IN (''1'')) ' +\n"
        + "//                           '      OR' +\n"
        + "//                           '      (TRUNC(IKAN_SAIBAN_SIJI_UPDATE_DATE) = :WHERE_TERM_DATE ' +\n"
        + "//                           '        AND IKAN_SAIBAN_GYOUMU_STATUS IN (''1'', ''5'') ) ' +\n"
        + "//                           '      OR' +\n"
        + "//                           '      (TRUNC(IKAN_SIKKO_SIJI_UPDATE_DATE) = :WHERE_TERM_DATE ' +\n"
        + "//                           '        AND IKAN_SIKKO_GYOUMU_STATUS IN (''1'', ''5'') ) ' +\n"
        + "//                           '    )' +\n"
        + "//                           '  ) ' +\n"
        + "                           '  SCH_TYPE = 1 ' +\n"
        + "                           '  AND ( ' +\n"
        + "                           '    (TRUNC(TERM_DATE) = :WHERE_TERM_DATE AND TERM_CODE IN (2070, 2080))' +\n"
        + "                           '    OR ' +\n"
        + "                           '    (TRUNC(NEXT_DATE) = :WHERE_TERM_DATE)' +\n"
        + "                           '    OR ' +\n"
        + "                           '    (TRUNC(IKAN_SIJI_UPDATE_DATE) = :WHERE_TERM_DATE)' +\n"
        + "                           '  ) ' +\n"
        + "// 2016/02/18 No86 Egashira Mod End\n"
        + "                           '  $(SET_OPERATOR_TEAM)' +\n"
        + "// 2016/02/18 No86 Egashira Add Start\n"
        + "                           ' ORDER BY' +\n"
        + "                           '   IKAN_NOW_GYOUMU_STATUS, IKAN_SIJI_STATUS, CUSTOMER_NO';\n"
        + "// 2016/02/18 No86 Egashira Add End";

    System.out.println(source);

    System.out.println("=============OUTPUT=============");

    String converted = source
        .replaceAll("\\s+\\/\\/.*", "")
        .replaceAll("\'(.*)\'", "$1")
        .replaceAll("''", "'")
        .replaceAll("\\+\\r\\n", "\r\n")  // xóa dấu + ở cuối dòng (Windows)
        .replaceAll("\\+\\n", "\n")  // xóa dấu + ở cuối dòng (Linux)
        .replaceAll("\\+$", "")  // xóa dấu + cuối cùng
        ;
    System.out.println(converted);
    System.out.println("=============FIELDS=============");

    String fields = converted
        .replaceAll("\\r\\n", " ")
        .replaceAll("\\r", " ")
        .replaceAll("\\n", " ")
        .replaceAll("[\\s\\S]*SELECT([\\s\\S]*)FROM[\\s\\S]*", "$1");

//    Pattern columnRegex  = Pattern.compile("[\\s\\S]*SELECT([\\s\\S]*)FROM[\\s\\S]*", Pattern.MULTILINE);
//    Matcher matcher = columnRegex.matcher(converted);
//    if(matcher.find()) {
//      System.out.println("Found match: " + matcher.group(1));
//    } else {
//      System.out.println("Not found");
//    }

    System.out.println(fields);
  }

}
