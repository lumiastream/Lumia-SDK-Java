package com.lumiastream.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LumiaEventType {

  STATES(47, "states"),
  CHAT(48, "chat"),
  COMMAND(49, "command"),
  TWITCH_POINTS(50, "twitch_points"),
  TWITCH_EXTENSIONS(51, "twitch_extesions"),
  TROVO_SPELL(52, "trovo_spell"),
  PULSE(53, "pulse"),
  ALERT(54, "alert");

  private final String value;
  private final Integer id;

  LumiaEventType(final Integer id, final String value) {
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
