package com.lumiastream.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class LumiaPackParam {

  private String value;
  private List<LumiaLight> lights;
  private Boolean hold;
  private Boolean skipQueue;
  private Platform platform;
  private String voice;
  private Integer volume;
  private Integer brightness;
  private Long duration;
  private Long transition;
  private ExtraSetting extraSetting;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public List<LumiaLight> getLights() {
    return lights;
  }

  public void setLights(List<LumiaLight> lights) {
    this.lights = lights;
  }

  public Boolean getHold() {
    return hold;
  }

  public void setHold(Boolean hold) {
    this.hold = hold;
  }

  public Boolean getSkipQueue() {
    return skipQueue;
  }

  public void setSkipQueue(Boolean skipQueue) {
    this.skipQueue = skipQueue;
  }

  public Platform getPlatform() {
    return platform;
  }

  public void setPlatform(Platform platform) {
    this.platform = platform;
  }

  public String getVoice() {
    return voice;
  }

  public void setVoice(String voice) {
    this.voice = voice;
  }

  public Integer getVolume() {
    return volume;
  }

  public void setVolume(Integer volume) {
    this.volume = volume;
  }

  public Integer getBrightness() {
    return brightness;
  }

  public void setBrightness(Integer brightness) {
    this.brightness = brightness;
  }

  public Long getDuration() {
    return duration;
  }

  public void setDuration(final Long duration) {
    this.duration = duration;
  }

  public Long getTransition() {
    return transition;
  }

  public void setTransition(Long transition) {
    this.transition = transition;
  }

  public ExtraSetting getExtraSetting() {
    return extraSetting;
  }

  public void setExtraSetting(ExtraSetting extraSetting) {
    this.extraSetting = extraSetting;
  }


  public static void main(String[] args) {
    final JsonObject put = new JsonObject().put("value", "some value");
    final LumiaPackParam lumiaPackParam = put.mapTo(LumiaPackParam.class);
    System.out.println(Json.encode(lumiaPackParam));
    System.out.println(lumiaPackParam.brightness);
    System.out.println(lumiaPackParam.value);
    System.out.println(Json.encode(LumiaCommandType.CHAT_COMMAND));
  }
}
