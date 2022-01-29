package com.lumiastream.client;

public class Lumia {

  public static LumiaWebSocketClient client() {
    return new LumiaWebSocketClient(new LumiaOptions());
  }
}
