package jp.co.nissho_ele.acc.acj.bean;

import java.util.List;

import jp.co.nissho_ele.acc.acj.entity.dto.[( ${serviceId} )]Pproc1Dto;
import jp.co.nissho_ele.acc.base_package.bean.BaseOutBean;

/**
 * [( ${serviceId} )]OutBean.java
 * --------------------------------------------------------------------------------------------*
 * History<br/>
 * Version YYYY/MM/DD Author       Content
 * --------------------------------------------------------------------------------------------*
 * 1.0     [(${currentDate})] NEV-[( ${author} )]   Create
 * --------------------------------------------------------------------------------------------*
 */
public class [( ${serviceId} )]OutBean extends BaseOutBean {

  /** serialVersionUID. */
  private static final long serialVersionUID = 1L;

  private [( ${serviceId} )]Pproc1Dto outDto = null;

  /**
   * @return outDto [( ${serviceId} )]Pproc1Dto
   */
  public [( ${serviceId} )]Pproc1Dto getOutDto() {
    return outDto;
  }

  /**
   * @param outDto [( ${serviceId} )]Pproc1Dto
   */
  public void setOutDto([( ${serviceId} )]Pproc1Dto outDto) {
    this.outDto = outDto;
  }
}
