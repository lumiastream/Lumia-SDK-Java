package com.lumiastream.client;

public class LumiaOptions {

  private String host;
  private Integer port;
  private String name;
  private String token;

  public LumiaOptions(final String host, final Integer port, final String name,
      final String token) {
    this.host = host;
    this.port = port;
    this.name = name;
    this.token = token;
  }

  public String getHost() {
    return host;
  }

  public String getToken() {
    return token;
  }

  public String getName() {
    return name;
  }

  public void setHost(final String host) {
    this.host = host;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(final Integer port) {
    this.port = port;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setToken(final String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return "LumiaOptions{" +
        "host='" + host + '\'' +
        ", name='" + name + '\'' +
        ", token='" + token + '\'' +
        '}';
  }
}
