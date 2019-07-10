package vn.nev.tools.pcctool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseController {

  protected final Logger LOG;

  public BaseController() {

    LOG = LoggerFactory.getLogger(this.getClass());

  }
}
