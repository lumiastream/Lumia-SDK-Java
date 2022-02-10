package com.lumiastream.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LumiaExternalActivityCommandType {
  ALERT("alert"),
  MIDI("midi"),
  OSC("osc"),
  ARTNET("artnet"),
  RGB_COLOR("rgb-color"),
  HEX_COLOR("hex-color"),
  CHAT_COMMAND("chat-command"),
  TWITCH_POINTS("twitch-points"),
  TWITCH_EXTENSION("twitch-extension"),
  TROVO_SPELLS("trovo-spells"),
  STUDIO_SCENE("studio-scene"),
  STUDIO_ANIMATION("studio-animation"),
  STUDIO_THEME("studio-theme"),
  CHATBOT_MESSAGE("chatbot-message"),
  TTS("tts"),
  GAMESGLOW_ALERT("gamesglow-alert"),
  GAMESGLOW_COMMAND("gamesglow-command"),
  GAMESGLOW_VARIABLE("gamesglow-variable"),
  GAMESGLOW_VIRTUALLIGHT("gamesglow-virtuallight");

  private final String value;

  LumiaExternalActivityCommandType(final String value) {

    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return this.value;
  }
}
