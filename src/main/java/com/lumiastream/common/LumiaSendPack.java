package com.lumiastream.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lumiastream.common.enums.LumiaExternalActivityCommandType;

@JsonInclude(Include.NON_NULL)
public class LumiaSendPack {
  @JsonProperty("type")
  private LumiaExternalActivityCommandType commandType;
  @JsonProperty("gamesGlowId")
  private String gamesGlowId;
  @JsonProperty("gamesGlowKey")
  private String gamesGlowKey;
  @JsonProperty("params")
  private LumiaPackParam packParam;

  public LumiaSendPack(final LumiaExternalActivityCommandType commandType, final LumiaPackParam packParam) {
    this.commandType = commandType;
    this.packParam = packParam;
  }

  public LumiaSendPack(final LumiaExternalActivityCommandType commandType, final LumiaPackParam packParam,
      final String gamesGlowId, final String gamesGlowKey) {
    this.commandType = commandType;
    this.gamesGlowId = gamesGlowId;
    this.gamesGlowKey = gamesGlowKey;
    this.packParam = packParam;
  }
}
