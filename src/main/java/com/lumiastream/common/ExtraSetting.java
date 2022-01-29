package com.lumiastream.common;

public class ExtraSetting {

  private String username;
  private Integer bits;

  public ExtraSetting(final String username,final Integer bits) {
    this.username = username;
    this.bits = bits;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public Integer getBits() {
    return bits;
  }

  public void setBits(final Integer bits) {
    this.bits = bits;
  }
}
