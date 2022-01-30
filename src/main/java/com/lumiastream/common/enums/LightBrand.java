package com.lumiastream.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LightBrand {

  HUE(116, "hue"),
  NANOLEAF(117, "nanoleaf"),
  NANOLEAF2(118, "nanoleaf2"),
  LIFX(119, "lifx"),
  TPLINK(120, "tplink"),
  YEELIGHT(121, "yeelight"),
  COLOLIGHT(122, "cololight"),
  TUYA(123, "tuya"),
  SMARTLIFE(124, "smartlife"),
  WYZE(125, "wyze"),
  WIZ(126, "wiz"),
  HOMEASSISTANT(127, "homeassistant"),
  GOVEE(128, "govee"),
  WLED(129, "wled"),
  MAGICHOME(130, "magichome"),
  LOGITECH(131, "logitech"),
  RAZER(132, "razer"),
  CORSAIR(133, "corsair"),
  STEELSERIES(134, "steelseries"),
  OVERLAY(135, "overlay"),
  ELGATO(136, "elgato");

  private final String value;
  private final Integer id;

  LightBrand(final Integer id, final String value) {
    this.id = id;
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return this.value;
  }

  public Integer getId() {
    return this.id;
  }
}
