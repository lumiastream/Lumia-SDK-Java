package com.lumiastream.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LumiaAlertValue {
  TWITCH_STREAM_LIVE("twitch-streamLive"),
  TWITCH_STREAM_OFFLINE("twitch-streamOffline"),
  TWITCH_FOLLOWER("twitch-follower"),
  TWITCH_SUBSCRIBER("twitch-subscriber"),
  TWITCH_HOST("twitch-host"),
  TWITCH_RAID("twitch-raid"),
  TWITCH_BITS("twitch-bits"),
  TWITCH_REDEMPTION("twitch-redemption"),
  TWITCH_HYPETRAIN_STARTED("twitch-hypetrainStarted"),
  TWITCH_HYPETRAIN_PROGRESSED("twitch-hypetrainProgressed"),
  TWITCH_HYPETRAIN_ENDED("twitch-hypetrainEnded"),
  YOUTUBE_MEMBER("youtube-member"),
  YOUTUBE_SUBSCRIBER("youtube-subscriber"),
  YOUTUBE_SUPERCHAT("youtube-supersticker"),
  YOUTUBE_SUPERSTICKER("youtube-supersticker"),
  FACEBOOK_FOLLOWER("facebook-follower"),
  FACEBOOK_REACTION("facebook-reaction"),
  FACEBOOK_STAR("facebook-star"),
  FACEBOOK_SUPPORT("facebook-support"),
  FACEBOOK_SHARE("facebook-share"),
  FACEBOOK_FAN("facebook-fan"),
  GLIMESH_SUBSCRIBER("glimesh-subscriber"),
  GLIMESH_FOLLOWER("glimesh-follower"),
  TROVO_SUBSCRIBER("trovo-subscriber"),
  TROVO_FOLLOWER("trovo-follower"),
  TROVO_SPELL("trovo-spell"),
  TIKTOK_FOLLOWER("tiktok-follower"),
  TIKTOK_LIKE("tiktok-like"),
  TIKTOK_GIFT("tiktok-gift"),
  STREAMLABS_DONATION("streamlabs-donation"),
  STREAMLABS_CHARITY("streamlabs-charity"),
  STREAMLABS_MERCH("streamlabs-merch"),
  STREAMLABS_REDEMPTION("streamlabs-redemption"),
  STREAMLABS_PRIMEGIFT("streamlabs-primegift"),
  STREAMELEMENTS_DONATION("streamelements-donation"),
  STREAMELEMENTS_MERCH("streamelements-merch"),
  STREAMELEMENTS_REDEMPTION("streamelements-redemption"),
  EXTRALIFE_DONATION("extralife-donation"),
  DONORDRIVE_DONATION("donordrive-donation"),
  TILTIFY_DONATION("tiltify-campaignDonation"),
  PAYPAL_PAYMENT_COMPLETE("paypal-paymentComplete"),
  PAYPAL_PAYMENT_DENIED("paypal-paymentDenied"),
  TIPEEESTREAM_DONATION("tipeeestream-donation"),
  TREATSTREAM_TREAT("treatstream-treat"),
  PATREON_PLEDGE("patreon-pledge"),
  OBS_SWITCHP_ROFILE("obs-switchprofile"),
  OBS_SWITCH_SCENE("obs-switchscene"),
  OBS_SCENE_ITEM_VISIBILITY("obs-sceneItemVisibility"),
  OBS_SWITCH_TRANSITION("obs-switch-transition"),
  OBS_TRANSITION_BEGIN("obs-transitionBegin"),
  OBS_TRANSITION_END("obs-transitionEnd"),
  OBS_STREAM_STARTING("obs-streamStarting"),
  OBS_STREAM_STOPPING("obs-streamStopping"),
  SLOBS_SWITCH_SCENE_COLLECTION("slobs-switchSceneCollection"),
  SLOBS_SWITCH_SCENE("slobs_switchScene"),
  SLOBS_SCENE_ITEM_VISIBILITY("slobs-sceneItemVisibility"),
  TWITTER_FOLLOWER("twitter-follower"),
  TWITTER_LIKE("twitter-like"),
  TWITTER_RETWEET("twitter-retweet"),
  SPOTIFY_SWITCH_SONG("spotify-switchSong"),
  SPOTIFY_SONG_PLAYED("spotify-songPlayed"),
  SPOTIFY_SONG_PAUSED("spotify-songPaused"),
  VLC_SWITCH_SONG("vlc-switchSong"),
  VLC_SONG_PLAYED("vlc-songPlayed"),
  VLC_SONG_PAUSED("vlc-songPaused"),
  PULSE_HEART_RATE("pulse_heartrate"),
  PULSE_CALORIES("pulse_calories"),
  WOOCOMMERCE_ORDER("woocommerce-order");

  private final String value;

  LumiaAlertValue(final String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return this.value;
  }
}
