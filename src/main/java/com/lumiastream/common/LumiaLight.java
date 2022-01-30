package com.lumiastream.common;

import com.lumiastream.common.enums.LightBrand;

public class LumiaLight {

  private LightBrand lightBrand;
  private Integer id;

  public LumiaLight(final LightBrand lightBrand,final Integer id) {
    this.lightBrand = lightBrand;
    this.id = id;
  }

  public LightBrand getLightBrand() {
    return lightBrand;
  }

  public void setLightBrand(final LightBrand lightBrand) {
    this.lightBrand = lightBrand;
  }

  public Integer getId() {
    return id;
  }

  public void setId(final Integer id) {
    this.id = id;
  }
}
