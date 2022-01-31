package com.lumiastream.client;

import com.lumiastream.common.LumiaLight;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MessageOptions {

  private Boolean skipQueue;
  private Boolean holdDefault;
  private Duration transition;
  private Duration duration;
  private List<LumiaLight> lights;

  public MessageOptions() {
    initializeDefaults();
  }

  private void initializeDefaults() {
    this.skipQueue = false;
    this.holdDefault = false;
    this.duration = Duration.ofMillis(0);
    this.transition = Duration.ofMillis(0);
    this.lights = new ArrayList<>();
  }

  public Boolean getSkipQueue() {
    return skipQueue;
  }

  public MessageOptions setSkipQueue(final Boolean skipQueue) {
    this.skipQueue = skipQueue;
    return this;
  }

  public Boolean getHoldDefault() {
    return holdDefault;
  }

  public MessageOptions setHoldDefault(final Boolean holdDefault) {
    this.holdDefault = holdDefault;
    return this;
  }

  public Duration getTransition() {
    return transition;
  }

  public MessageOptions setTransition(final Duration transition) {
    this.transition = transition;
    return this;
  }


  public List<LumiaLight> getLights() {
    return lights;
  }

  public MessageOptions setLights(final List<LumiaLight> lights) {
    this.lights = lights;
    return this;
  }

  public Duration getDuration() {
    return duration;
  }

  public MessageOptions setDuration(final Duration duration) {
    this.duration = duration;
    return this;
  }
}

