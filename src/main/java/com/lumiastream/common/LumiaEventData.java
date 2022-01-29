package com.lumiastream.common;

public class LumiaEventData {
private String username;
private String command;

  public LumiaEventData(final String username, final String command) {
    this.username = username;
    this.command = command;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(final String command) {
    this.command = command;
  }
}
