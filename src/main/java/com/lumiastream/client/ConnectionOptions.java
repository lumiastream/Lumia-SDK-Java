package com.lumiastream.client;

public class ConnectionOptions {

  private String host;
  private Integer port;
  private String name;
  private String token;

  public String getHost() {
    return host;
  }

  public String getToken() {
    return token;
  }

  public String getName() {
    return name;
  }

  public ConnectionOptions setHost(final String host) {
    this.host = host;
    return this;
  }

  public Integer getPort() {
    return port;
  }

  public ConnectionOptions setPort(final Integer port) {
    this.port = port;
    return this;
  }

  public ConnectionOptions setName(final String name) {
    this.name = name;
    return this;
  }

  public ConnectionOptions setToken(final String token) {
    this.token = token;
    return this;
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
