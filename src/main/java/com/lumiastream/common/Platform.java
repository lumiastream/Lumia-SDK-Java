package com.lumiastream.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Platform {

  TWITCH(111, "twitch"),
  YOUTUBE(112, "youtube"),
  FACEBOOK(113, "facebook"),
  TROVO(114, "trovo"),
  GLIMESH(115, "glimesh");
  
  private final String value;
  private final Integer id;

  Platform(final Integer id, final String value) {
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
