package com.lumiastream.common;

public class Rgb {
private Integer r;
private Integer g;
private Integer b;

  public Rgb(final Integer r, final Integer g, final Integer b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  public Integer getR() {
    return r;
  }

  public void setR(final Integer r) {
    this.r = r;
  }

  public Integer getG() {
    return g;
  }

  public void setG(final Integer g) {
    this.g = g;
  }

  public Integer getB() {
    return b;
  }

  public void setB(final Integer b) {
    this.b = b;
  }
}
