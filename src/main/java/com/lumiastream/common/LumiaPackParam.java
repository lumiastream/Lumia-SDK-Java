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

  public LumiaPackParam setValue(final String value) {
    this.value = value;
    return this;
  }

  public List<LumiaLight> getLights() {
    return lights;
  }

  public LumiaPackParam setLights(final List<LumiaLight> lights) {
    this.lights = lights;
    return this;
  }

  public Boolean getHold() {
    return hold;
  }

  public LumiaPackParam setHold(final Boolean hold) {
    this.hold = hold;
    return this;
  }

  public Boolean getSkipQueue() {
    return skipQueue;
  }

  public LumiaPackParam setSkipQueue(final Boolean skipQueue) {
    this.skipQueue = skipQueue;
    return this;
  }

  public Platform getPlatform() {
    return platform;
  }

  public LumiaPackParam setPlatform(final Platform platform) {
    this.platform = platform;
    return this;
  }

  public String getVoice() {
    return voice;
  }

  public LumiaPackParam setVoice(final String voice) {
    this.voice = voice;
    return this;
  }

  public Integer getVolume() {
    return volume;
  }

  public LumiaPackParam setVolume(final Integer volume) {
    this.volume = volume;
    return this;
  }

  public Integer getBrightness() {
    return brightness;
  }

  public LumiaPackParam setBrightness(final Integer brightness) {
    this.brightness = brightness;
    return this;
  }

  public Long getDuration() {
    return duration;
  }

  public LumiaPackParam setDuration(final Long duration) {
    this.duration = duration;
    return this;
  }

  public Long getTransition() {
    return transition;
  }

  public LumiaPackParam setTransition(final Long transition) {
    this.transition = transition;
    return this;
  }

  public ExtraSetting getExtraSetting() {
    return extraSetting;
  }

  public LumiaPackParam setExtraSetting(final ExtraSetting extraSetting) {
    this.extraSetting = extraSetting;
    return this;
  }
}
