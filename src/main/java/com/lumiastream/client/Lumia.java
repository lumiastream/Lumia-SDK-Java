package com.lumiastream.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lumiastream.common.LumiaPackParam;
import com.lumiastream.common.LumiaSendPack;
import com.lumiastream.common.Rgb;
import com.lumiastream.common.enums.LumiaAlertValue;
import com.lumiastream.common.enums.LumiaExternalActivityCommandType;
import com.lumiastream.common.enums.Platform;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.groups.UniOnFailure;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.buffer.Buffer;
import io.vertx.mutiny.core.http.WebSocket;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

public class Lumia {

  private WebSocket webSocket;
  private final ConnectionOptions lumiaOptions;
  private static final Logger logger = Logger
      .getLogger(Lumia.class.getCanonicalName());

  public WebSocket getWebSocket() {
    return webSocket;
  }

  private Lumia(final ConnectionOptions lumiaOptions) {
    this.lumiaOptions = lumiaOptions;
    logger.info(() -> String.format("Initializing:- Options: %s", lumiaOptions.toString()));
  }

  public static Lumia getInstance(final ConnectionOptions lumiaOptions) {
    registerJavaDateTimeEncoders();
    return new Lumia(lumiaOptions);
  }

  private static void registerJavaDateTimeEncoders() {
    final ObjectMapper mapper = DatabindCodec.mapper();
    mapper.registerModule(new JavaTimeModule());

    final ObjectMapper prettyMapper = DatabindCodec.prettyMapper();
    prettyMapper.registerModule(new JavaTimeModule());
  }

  public Uni<Boolean> connect(final boolean shouldAutoReconnect) {
    final StringBuilder uri = new StringBuilder().append("/api?token=")
        .append(lumiaOptions.getToken())
        .append("&name=").append(lumiaOptions.getName());
    logger.info(() -> String.format("Connecting:- URI: %s", uri));

    final UniOnFailure<Boolean> onFailure = Vertx.vertx().createHttpClient()
        .webSocket(lumiaOptions.getPort(), lumiaOptions.getHost(), uri.toString())
        .onItem().transform(webSocket1 -> {
          webSocket = webSocket1;
          logger.info(() -> String.format("Connected:- Closed Status: %s", webSocket1.isClosed()));
          return webSocket.isClosed();
        })
        .onFailure(throwable -> {
          throwable.printStackTrace();
          return true;
        });
    if (shouldAutoReconnect) {
      return onFailure.retry().indefinitely();
    } else {
      return onFailure.recoverWithItem(true);
    }
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
    final CountDownLatch countDownLatch = new CountDownLatch(1);
    JsonObject result = new JsonObject();
    if (webSocket != null) {
      logger
          .info(() -> String.format("Sending Websocket Message:- Data: %s: isClosed: %s", json, webSocket.isClosed()));
      webSocket.handler(buffer -> result.mergeIn(new JsonObject(buffer.toString())))
          .write(Buffer.buffer(json));
      countDownLatch.countDown();
    } else {
      result.put("message", "WebSocket not connected");
      countDownLatch.countDown();
    }
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return Multi.createFrom().item(result);
  }

  public Multi<JsonObject> send(final LumiaSendPack pack) {
    final String packString = Json.encode(pack);
    final JsonObject message = new JsonObject().put("lsorigin", "lumia-sdk");
    final JsonObject merged = message.mergeIn(new JsonObject(packString));
    logger.info(() -> String.format("Data: %s", merged.encode()));
    return sendWebsocketMessage(merged.encode());
  }

  public Multi<JsonObject> sendAlert(final LumiaAlertValue alert) {
    final LumiaPackParam packParam = new LumiaPackParam().setValue(alert.getValue());
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.ALERT, packParam);
    logger.info(() -> String.format("Alerting :- Data: %s", Json.encode(pack)));
    return send(pack);
  }

  public Multi<JsonObject> sendChatBot(final Platform platform, final String text) {
    final LumiaPackParam packParam = new LumiaPackParam().setValue(text).setPlatform(platform);
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.CHATBOT_MESSAGE,
        packParam);
    logger.info(() -> String.format("Chat Botting :- Data: %s", Json.encode(pack)));
    return send(pack);
  }

  public Multi<JsonObject> sendColor(final Rgb rgb, final Integer brightness,
      final MessageOptions messageOptions) {
    final LumiaPackParam packParam = new LumiaPackParam().setValue(Json.encode(rgb))
        .setBrightness(brightness).setHold(messageOptions.getHoldDefault());
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.RGB_COLOR,
        packParam);
    logger.info(() -> String.format("Coloring :- Data: %s", Json.encode(pack)));
    return send(pack);
  }

  public Multi<JsonObject> sendCommand(final String command, final Boolean hold,
      final Boolean skipQueue) {
    final LumiaPackParam packParam = new LumiaPackParam().setValue(command).setHold(hold)
        .setSkipQueue(skipQueue);
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.CHAT_COMMAND,
        packParam);
    logger.info(() -> String.format("Commanding :- Data: %s", Json.encode(pack)));
    return send(pack);
  }

  public Multi<JsonObject> sendBrightness(final Integer brightness,
      final MessageOptions messageOptions) {
    final LumiaPackParam packParam = new LumiaPackParam().setBrightness(brightness)
        .setDuration(messageOptions.getDuration()).setTransition(messageOptions.getTransition());
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.RGB_COLOR,
        packParam);
    logger.info(() -> String.format("Brightness :- Data: %s", Json.encode(pack)));
    return send(pack);
  }

  public Multi<JsonObject> sendTts(final String text, final Integer volume, final String voice) {
    final LumiaPackParam packParam = new LumiaPackParam().setValue(text).setVolume(volume)
        .setVoice(voice);
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.TTS, packParam);
    logger.info(() -> String.format("TTSing :- Data: %s", Json.encode(pack)));
    return send(pack);
  }

  // Games glow functions
  public Multi<JsonObject> getGamesGlowSettings() {
    final JsonObject getInfoPayload = new JsonObject().put("gamesGlowName", this.lumiaOptions.getName())
        .put("method", "gamesGlowSettings");
    final Buffer buffer = Buffer.buffer(getInfoPayload.toString());
    logger.info(() -> String.format("Getting Info:- Data: %s", buffer.toString()));
    return sendWebsocketMessage(buffer.toString());
  }

  public Multi<JsonObject> sendGamesGlowAlert(final String glowId, final String value) {
    final LumiaPackParam packParam = new LumiaPackParam().setValue(value);
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.GAMESGLOW_ALERT,
        packParam, this.lumiaOptions.getName(), glowId);
    logger.info(() -> String.format("GamesgLow Alert :- Data: %s", Json.encode(pack)));
    return send(pack);
  }

  public Multi<JsonObject> sendGamesGlowCommand(final String glowId, final String value) {
    final LumiaPackParam packParam = new LumiaPackParam().setValue(value);
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.GAMESGLOW_COMMAND,
        packParam, this.lumiaOptions.getName(), glowId);
    logger.info(() -> String.format("GamesgLow Command :- Data: %s", Json.encode(pack)));
    return send(pack);
  }

  public Multi<JsonObject> sendGamesGlowVariableUpdate(final String glowId, final String value) {
    final LumiaPackParam packParam = new LumiaPackParam().setValue(value);
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.GAMESGLOW_VARIABLE,
        packParam, this.lumiaOptions.getName(), glowId);
    logger.info(() -> String.format("GamesgLow Variable :- Data: %s", Json.encode(pack)));
    return send(pack);
  }

  public Multi<JsonObject> sendGamesGlowVirtualLightsChange(final String glowId, final String value) {
    final LumiaPackParam packParam = new LumiaPackParam().setValue(value);
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.GAMESGLOW_VIRTUALLIGHT,
        packParam, this.lumiaOptions.getName(), glowId);
    logger.info(() -> String.format("GamesgLow Virtual Light :- Data: %s", Json.encode(pack)));
    return send(pack);
  }
}
