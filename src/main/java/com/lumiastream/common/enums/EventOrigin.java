package com.lumiastream.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EventOrigin {

  LUMIA("lumia"),
  TWITCH("twitch"),
  YOUTUBE("youtube"),
  FACEBOOK("facebook"),
  GLIMESH("glimesh"),
  TROVO("trovo"),
  STREAMLABS("streamlabs"),
  STREAMELEMENTS("streamelements"),
  EXTRALIFE("extralife"),
  DONORDRIVE("donordrive"),
  TILTIFY("tiltify"),
  PATREON("patreon"),
  TIPEEESTREAM("tipeeestream"),
  TREATSTREAM("treatstream"),
  DISCORD("discord"),
  OBS("obs"),
  SLOBS("slobs"),
  PULSOID("pulsoid"),
  PAYPAL("paypal");

  private final String value;

  EventOrigin(final String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return this.value;
  }
}
