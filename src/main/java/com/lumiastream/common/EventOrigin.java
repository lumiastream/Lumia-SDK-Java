package com.lumiastream.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EventOrigin {

  LUMIA(137, "lumia"),
  TWITCH(138, "twitch"),
  YOUTUBE(139, "youtube"),
  FACEBOOK(140, "facebook"),
  GLIMESH(141, "glimesh"),
  TROVO(142, "trovo"),
  STREAMLABS(143, "streamlabs"),
  STREAMELEMENTS(144, "streamelements"),
  EXTRALIFE(145, "extralife"),
  DONORDRIVE(146, "donordrive"),
  TILTIFY(147, "tiltify"),
  PATREON(148, "patreon"),
  TIPEEESTREAM(149, "tipeeestream"),
  TREATSTREAM(150, "treatstream"),
  DISCORD(151, "discord"),
  OBS(152, "obs"),
  SLOBS(153, "slobs"),
  PULSOID(154, "pulsoid"),
  PAYPAL(155, "paypal");

  private final String value;
  private final Integer id;

  EventOrigin(final Integer id, final String value) {
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
