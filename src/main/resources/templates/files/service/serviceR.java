package jp.co.nissho_ele.acc.acj.service;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import jp.co.nissho_ele.acc.acj.bean.[( ${serviceId} )]InBean;
import jp.co.nissho_ele.acc.acj.bean.[( ${serviceId} )]OutBean;
import jp.co.nissho_ele.acc.acj.entity.access.[( ${accessName} )];
import jp.co.nissho_ele.acc.acj.entity.dto.[( ${dtoName} )];
import jp.co.nissho_ele.acc.base_package.bean.BaseBean;
import jp.co.nissho_ele.acc.base_package.common.base.ACCMessageConst;
import jp.co.nissho_ele.acc.base_package.service.ServiceBase;

/**
 * [( ${serviceId} )]Service.java
 * --------------------------------------------------------------------------------------------*
 * History<br/>
 * Version YYYY/MM/DD Author       Content
 * --------------------------------------------------------------------------------------------*
 * 1.0     [(${currentDate})] NEV-[( ${author} )]   Create
 * --------------------------------------------------------------------------------------------*
 */
@Remote
@Stateless(mappedName = "[( ${serviceId} )]")
public class [( ${serviceId} )]Service extends ServiceBase implements [( ${serviceId} )]ServiceRemote, Serializable {

  /** serialVersionUID. */
  private static final long serialVersionUID = 1L;

  @Override
  public final BaseBean execProcess(final Connection conn, final BaseBean bean) throws Exception {
    // beanをInBeanにキャスト
    [( ${serviceId} )]InBean inBean = null;
    if (bean instanceof [( ${serviceId} )]InBean) {
      inBean = ([( ${serviceId} )]InBean) bean;
    }

    if (inBean == null) {
      throw createAccException(ACCMessageConst.CTG_SV1_EXP04, ACCMessageConst.MSG_SV1_EXP04);
    }

    // outBeanのインスタンスを生成する。
    [( ${serviceId} )]OutBean outBean = new [( ${serviceId} )]OutBean();

    // baseInfo を outBeanにコピーする
    outBean.setBaseInfo(inBean.getBaseInfo());

    // Setting inDto
    [( ${dtoName} )] inDto = new [( ${dtoName} )]();
[(${copyPropertiesString})]

    // Create Access Object to run method select1
    [( ${accessName} )] access = new [( ${accessName} )]();
    List<[( ${dtoName} )]> outDtoList = access.select1(conn, inDto);

    // Set value for out bean.
    if (outDtoList != null && outDtoList.size() > 0) {
      outBean.setOutDto(outDtoList);
    }

    return outBean;
  }
}
