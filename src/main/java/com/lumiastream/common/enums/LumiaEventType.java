package com.lumiastream.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LumiaEventType {

  STATES("states"),
  CHAT("chat"),
  COMMAND("command"),
  TWITCH_POINTS("twitch-points"),
  TWITCH_EXTENSIONS("twitch-extesions"),
  TROVO_SPELL("trovo-spell"),
  PULSE( "pulse"),
  ALERT("alert");

  private final String value;

  LumiaEventType( final String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return this.value;
  }
}
