package com.lumiastream.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LumiaAlertValue {
  TWITCH_FOLLOWER(16, "twitch_follower"),
  TWITCH_SUBSCRIBER(17, "twitch_subscriber"),
  TWITCH_BITS(18, "twitch_bits"),
  TWITCH_HOST(19, "twitch_host"),
  TWITCH_RAID(20, "twitch_raid"),
  YOUTUBE_SUBSCRIBER(21, "youtube_subscriber"),
  YOUTUBE_SUPERCHAT(22, "youtube_supersticker"),
  YOUTUBE_SUPERSTICKER(23, "youtube_supersticker"),
  YOUTUBE_MEMBER(24, "youtube_member"),
  FACEBOOK_FOLLOWER(25, "facebook_follower"),
  FACEBOOK_REACTION(26, "facebook_reaction"),
  FACEBOOK_STAR(27, "facebook_star"),
  FACEBOOK_SUPPORT(28, "facebook_support"),
  FACEBOOK_SHARE(29, "facebook_share"),
  FACEBOOK_FAN(30, "facebook_fan"),
  TROVO_FOLLOWER(31, "trovo_follower"),
  TROVO_SUBSCRIBER(32, "trovo_subscriber"),
  STREAMLABS_DONATION(33, "streamlabs_donation"),
  STREAMLABS_MERCH(34, "streamlabs_merch"),
  STREAMLABS_REDEMPTION(35, "streamlabs_redemption"),
  STREAMLABS_PRIMEGIFT(36, "streamlabs_primegift"),
  STREAMELEMENTS_DONATION(37, "streamelements_donation"),
  OBS_SWITCHPROFILE(38, "obs_switchprofile"),
  OBS_SWITCHSCENE(39, "obs_switchscene"),
  OBS_SWITCH_TRANSITION(40, "obs_switch_transition"),
  OBS_STREAMSTARTING(41, "obs_streamstarting"),
  OBS_STREAMSTOPPING(42, "obs_streamstopping"),
  SLOBS_SWITCHSCENE(43, "slobs_switchscene"),
  TREATSTREAM_TREAT(44, "treatstream_treat"),
  PULSE_HEARTRATE(45,"pulse_heartrate"),
  PULSE_CALORIES(46, "pulse_calories");

  private final String value;
  private final Integer id;

  LumiaAlertValue(final Integer id, final String value) {
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
