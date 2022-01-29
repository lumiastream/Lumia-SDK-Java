package com.lumiastream.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LumiaCommandType {
  ALERT(1, "alert"),
  MIDI(2, "midi"),
  OSC(3, "osc"),
  ARTNET(4, "artnet"),
  RGB_COLOR(5, "rgb_color"),
  HEX_COLOR(6, "hex_color"),
  CHAT_COMMAND(7, "chat_command"),
  TWITCH_POINTS(8, "twitch_points"),
  TWITCH_EXTENSION(9, "twitch_extension"),
  TROVO_SPELLS(10, "trovo_spells"),
  STUDIO_SCENE(11, "studio_scene"),
  STUDIO_ANIMATION(12, "studio_animation"),
  STUDIO_THEME(13, "studio_theme"),
  CHATBOT_MESSAGE(14, "chatbot_message"),
  TTS(15, "tts");

  private final String value;
  private final Integer id;

  LumiaCommandType(final Integer id, final String value) {
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
