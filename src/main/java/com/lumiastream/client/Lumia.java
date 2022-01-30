package com.lumiastream.client;

public class Lumia {

  public static LumiaWebSocketClient client(final LumiaOptions lumiaOptions) {
    return new LumiaWebSocketClient(lumiaOptions);
  }
}
