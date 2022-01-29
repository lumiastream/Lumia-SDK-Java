package com.lumiastream.client;


import com.lumiastream.common.LumiaAlertValue;
import com.lumiastream.common.LumiaCommandType;
import com.lumiastream.common.LumiaLight;
import com.lumiastream.common.LumiaPackParam;
import com.lumiastream.common.LumiaSendPack;
import com.lumiastream.common.Platform;
import com.lumiastream.common.Rgb;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.buffer.Buffer;
import io.vertx.mutiny.core.http.WebSocket;
import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

public class LumiaWebSocketClient {

  private WebSocket webSocket;
  private final LumiaOptions lumiaOptions;
  private static final Logger logger = Logger
      .getLogger(LumiaWebSocketClient.class.getCanonicalName());

  public WebSocket getWebSocket() {
    return webSocket;
  }

  LumiaWebSocketClient(final LumiaOptions lumiaOptions) {
    this.lumiaOptions = lumiaOptions;
    logger.info(() -> String.format("Initializing:- Options: %s", lumiaOptions.toString()));
  }

  public Uni<Boolean> connect() {

    final StringBuilder uri = new StringBuilder().append("/api?token=")
        .append(lumiaOptions.getToken())
        .append("&name=").append(lumiaOptions.getName());
    logger.info(() -> String.format("Connecting:- URI: %s", uri));

    return Vertx.vertx().createHttpClient().webSocket(lumiaOptions.getPort(), lumiaOptions.getHost(), uri.toString())
        .onItem().transform(webSocket1 -> {
          webSocket = webSocket1;
          logger.info(() -> String.format("Connected:- Closed Status: %s", webSocket1.isClosed()));
          return webSocket.isClosed();
        })
        .onFailure(throwable -> {
          throwable.printStackTrace();
          return true;
        }).recoverWithItem(true);
  }

  public Multi<JsonObject> getInfo() {
    final JsonObject getInfoPayload = new JsonObject().put("retrieve", true)
        .put("method", "retrieve");
    final Buffer buffer = Buffer.buffer(getInfoPayload.toString());
    logger.info(() -> String.format("Getting Info:- Data: %s", buffer.toString()));
    return sendWebsocketMessage(buffer.toString());
  }

  public Multi<JsonObject> stop() {
    final JsonObject getInfoPayload = new JsonObject().put("method", "stop");
    final Buffer buffer = Buffer.buffer(getInfoPayload.toString());
    logger.info(() -> String.format("Stopping:- Data: %s", buffer.toString()));
    return sendWebsocketMessage(buffer.toString());
  }

  private Multi<JsonObject> sendWebsocketMessage(final String json) {
    logger.info(() -> String.format("Sending Websocket Message:- Data: %s: isClosed: %s", json, webSocket.isClosed()));
    JsonObject result = new JsonObject();
    webSocket.handler(buffer -> result.mergeIn(new JsonObject(buffer.toString()))).writeAndForget(Buffer.buffer(json));

    return Multi.createFrom().item(result);
  }

  public Multi<JsonObject> send(final LumiaSendPack pack) {
    final String packString = Json.encode(pack);
    final JsonObject message = new JsonObject().put("lsorigin", "lumia-java-sdk");
    final JsonObject merged = message.mergeIn(new JsonObject(packString));
    logger.info(() -> String.format("Getting Info:- Data: %s", merged.encode()));
    return sendWebsocketMessage(merged.encode());
  }

  public Multi<JsonObject> sendAlert(final LumiaAlertValue alert) {
    final LumiaPackParam packParam = new LumiaPackParam();
    packParam.setValue(alert.getValue());
    final LumiaSendPack pack = new LumiaSendPack(LumiaCommandType.ALERT, packParam);
    logger.info(() -> String.format("Alerting :- Data: %s", Json.encode(pack)));
    return send(pack);
  }

  public Multi<JsonObject> sendChatBot(final Platform platform, final String text) {
    final LumiaPackParam packParam = new LumiaPackParam();
    packParam.setValue(text);
    packParam.setPlatform(platform);
    final LumiaSendPack pack = new LumiaSendPack(LumiaCommandType.CHATBOT_MESSAGE, packParam);
    logger.info(() -> String.format("Chat Botting :- Data: %s", Json.encode(pack)));
    return send(pack);
  }

  public Multi<JsonObject> sendColor(final Rgb rgb, final Integer brightness,
      final Duration duration, final Duration transition, final Boolean def,
      final Boolean skipQueue, final List<LumiaLight> lights) {

    final LumiaPackParam packParam = new LumiaPackParam();
    packParam.setValue(Json.encode(rgb));
    packParam.setBrightness(brightness);
    packParam.setDuration(duration.toMillis());
    packParam.setTransition(transition.toMillis());
    packParam.setHold(def);
    packParam.setSkipQueue(skipQueue);
    packParam.setLights(lights);
    final LumiaSendPack pack = new LumiaSendPack(LumiaCommandType.RGB_COLOR, packParam);
    logger.info(() -> String.format("Coloring :- Data: %s", Json.encode(pack)));
    return send(pack);
  }

  public Multi<JsonObject> sendCommand(final LumiaCommandType command, final Boolean def,
      final Boolean skipQueue) {
    final LumiaPackParam packParam = new LumiaPackParam();
    packParam.setValue(command.getValue());
    packParam.setHold(def);
    packParam.setSkipQueue(skipQueue);
    final LumiaSendPack pack = new LumiaSendPack(LumiaCommandType.CHAT_COMMAND, packParam);
    logger.info(() -> String.format("Commanding :- Data: %s", Json.encode(pack)));
    return send(pack);
  }

  public Multi<JsonObject> sendBrightness(final Integer brightness, final Duration transition,
      final Boolean skipQueue) {
    final LumiaPackParam packParam = new LumiaPackParam();
    packParam.setBrightness(brightness);
    packParam.setTransition(transition.toMillis());
    packParam.setSkipQueue(skipQueue);
    final LumiaSendPack pack = new LumiaSendPack(LumiaCommandType.RGB_COLOR, packParam);
    logger.info(() -> String.format("Brightness :- Data: %s", Json.encode(pack)));
    return send(pack);
  }

  public Multi<JsonObject> sendTts(final String text, final Integer volume, final String voice) {
    final LumiaPackParam packParam = new LumiaPackParam();
    packParam.setValue(text);
    packParam.setVolume(volume);
    packParam.setVoice(voice);
    final LumiaSendPack pack = new LumiaSendPack(LumiaCommandType.TTS, packParam);
    logger.info(() -> String.format("TTSing :- Data: %s", Json.encode(pack)));
    return send(pack);
  }

}
