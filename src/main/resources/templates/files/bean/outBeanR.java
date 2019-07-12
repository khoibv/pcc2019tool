package jp.co.nissho_ele.acc.acj.bean;

import java.util.List;

import jp.co.nissho_ele.acc.acj.entity.dto.[( ${serviceId} )]Rselect1Dto;
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

  /** List of [( ${serviceId} )]Rselect1Dto. */
  private List<[( ${serviceId} )]Rselect1Dto> outDto;

  /**
   * @return outDto [( ${serviceId} )]Rselect1Dto
   */
  public List<[( ${serviceId} )]Rselect1Dto> getOutDto() {
    return outDto;
  }

  /**
   * @param outDto [( ${serviceId} )]Rselect1Dto
   */
  public void setOutDto(List<[( ${serviceId} )]Rselect1Dto> outDto) {
    this.outDto = outDto;
  }
}
