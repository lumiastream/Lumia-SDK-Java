package com.lumiastream.client;

public class LumiaOptions {

  private String host = "127.0.0.1";
  private Integer port = 39231;
  private String name = "lumia-java-sdk";
  private String token = "39231";

  public String getHost() {
    return host;
  }

  public String getToken() {
    return token;
  }

  public String getName() {
    return name;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setToken(String token) {
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
