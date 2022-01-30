package com.lumiastream.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class LumiaSendPack {
  @JsonProperty("type")
  private LumiaCommandType commandType;
  @JsonProperty("params")
  private LumiaPackParam packParam;

  public LumiaSendPack(final LumiaCommandType commandType, final LumiaPackParam packParam) {
    this.commandType = commandType;
    this.packParam = packParam;
  }
}
