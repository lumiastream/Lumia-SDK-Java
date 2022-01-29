package com.lumiastream.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LumiaSendPack {
  private LumiaCommandType commandType;
  @JsonProperty("params")
  private LumiaPackParam packParam;

  public LumiaSendPack(final LumiaCommandType commandType, final LumiaPackParam packParam) {
    this.commandType = commandType;
    this.packParam = packParam;
  }
}
