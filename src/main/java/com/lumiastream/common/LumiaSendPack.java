package com.lumiastream.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lumiastream.common.enums.LumiaExternalActivityCommandType;

@JsonInclude(Include.NON_NULL)
public class LumiaSendPack {
  @JsonProperty("type")
  private LumiaExternalActivityCommandType commandType;
  @JsonProperty("gamesGlowName")
  private String gamesGlowName;
  @JsonProperty("glowId")
  private String glowId;
  @JsonProperty("params")
  private LumiaPackParam packParam;

  public LumiaSendPack(final LumiaExternalActivityCommandType commandType, final LumiaPackParam packParam,
      final String gamesGlowName, final String glowId) {
    this.commandType = commandType;
    this.gamesGlowName = gamesGlowName;
    this.glowId = glowId;
    this.packParam = packParam;
  }

  public LumiaSendPack(final LumiaExternalActivityCommandType commandType, final LumiaPackParam packParam) {
    this.commandType = commandType;
    this.packParam = packParam;
  }
}
