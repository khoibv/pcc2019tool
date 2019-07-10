package vn.nev.tools.pcctool.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseService {

  protected final Logger LOG;

  public BaseService() {
    LOG = LoggerFactory.getLogger(this.getClass());
  }

}
