package com.lumiastream.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lumiastream.common.LumiaPackParam;
import com.lumiastream.common.LumiaSendPack;
import com.lumiastream.common.Rgb;
import com.lumiastream.common.enums.LumiaAlertValue;
import com.lumiastream.common.enums.LumiaExternalActivityCommandType;
import com.lumiastream.common.enums.Platform;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.WebSocket;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;
import java.util.HashMap;
import java.util.logging.Logger;

public class Lumia {

  HashMap<Integer, Handler<Buffer>> hashMap = new HashMap<Integer , Handler<Buffer>>();
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

  public Promise<Boolean> connect(final boolean shouldAutoReconnect) {
    final Promise<Boolean> result = Promise.promise();
    final StringBuilder uri = new StringBuilder().append("/api?token=")
        .append(lumiaOptions.getToken())
        .append("&name=").append(lumiaOptions.getName());
    logger.info(() -> String.format("Connecting:- URI: %s", uri));

    Vertx.vertx().createHttpClient()
        .webSocket(lumiaOptions.getPort(), lumiaOptions.getHost(), uri.toString())
        .onSuccess(new Handler<WebSocket>() {
          @Override
          public void handle(WebSocket event) {
            webSocket = event;
            result.complete(event.isClosed());
          }
        })
        .onFailure(new Handler<Throwable>() {
          @Override
          public void handle(Throwable event) {
            event.printStackTrace();
            result.complete(true);
          }
        });
    return result;
  }

  public void getInfo(final Handler<Buffer> function) {
    final JsonObject getInfoPayload = new JsonObject().put("retrieve", true)
        .put("method", "retrieve");
    final Buffer buffer = Buffer.buffer(getInfoPayload.toString());
    logger.info(() -> String.format("Getting Info:- Data: %s", buffer.toString()));
    sendWebsocketMessage(buffer.toJsonObject(),
        function);
  }

  public void stop(
      final Handler<Buffer> function) {
    final JsonObject getInfoPayload = new JsonObject().put("method", "stop");
    final Buffer buffer = Buffer.buffer(getInfoPayload.toString());
    logger.info(() -> String.format("Stopping:- Data: %s", buffer.toString()));
    sendWebsocketMessage(buffer.toJsonObject(),
        function);
  }

  private void sendWebsocketMessage(final JsonObject json, final Handler<Buffer> handler) {
    final int context = handler.hashCode();
    hashMap.put(context, handler);
    json.put("context", context);
    if (webSocket != null) {
      logger.info(() -> String.format("Sending Websocket Message:- Data: %s: isClosed: %s"
          , json, webSocket.isClosed()));
      webSocket.handler(new Handler<Buffer>() {
        @Override
        public void handle(Buffer event) {
          final Integer context = event.toJsonObject().getInteger("context");
          hashMap.get(context).handle(event);
          hashMap.remove(context);
        }
      }).write(json.toBuffer());
    }
  }

  public void send(final LumiaSendPack pack, final Handler<Buffer> function) {

    final String packString = Json.encode(pack);
    final JsonObject message = new JsonObject().put("lsorigin", "lumia-sdk");
    final JsonObject merged = message.mergeIn(new JsonObject(packString));
    logger.info(() -> String.format("Data: %s", merged.encode()));
    sendWebsocketMessage(merged, function);
  }

  public void sendAlert(final LumiaAlertValue alert, final Handler<Buffer> function) {
    final LumiaPackParam packParam = new LumiaPackParam().setValue(alert.getValue());
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.ALERT, packParam);
    logger.info(() -> String.format("Alerting :- Data: %s", Json.encode(pack)));
    send(pack, function);
  }

  public void sendChatBot(final Platform platform, final String text,
      final Handler<Buffer> function) {
    final LumiaPackParam packParam = new LumiaPackParam().setValue(text).setPlatform(platform);
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.CHATBOT_MESSAGE,
        packParam);
    logger.info(() -> String.format("Chat Botting :- Data: %s", Json.encode(pack)));
    send(pack, function);
  }

  public void sendColor(final Rgb rgb, final Integer brightness,
      final MessageOptions messageOptions, final Handler<Buffer> function) {
    final LumiaPackParam packParam = new LumiaPackParam().setValue(Json.encode(rgb))
        .setBrightness(brightness).setHold(messageOptions.getHoldDefault());
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.RGB_COLOR,
        packParam);
    logger.info(() -> String.format("Coloring :- Data: %s", Json.encode(pack)));
    send(pack, function);
  }

  public void sendCommand(final String command, final Boolean hold,
      final Boolean skipQueue, final Handler<Buffer> function) {
    final LumiaPackParam packParam = new LumiaPackParam().setValue(command).setHold(hold)
        .setSkipQueue(skipQueue);
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.CHAT_COMMAND,
        packParam);
    logger.info(() -> String.format("Commanding :- Data: %s", Json.encode(pack)));
    send(pack, function);
  }

  public void sendBrightness(final Integer brightness,
      final MessageOptions messageOptions, final Handler<Buffer> function) {
    final LumiaPackParam packParam = new LumiaPackParam().setBrightness(brightness)
        .setDuration(messageOptions.getDuration()).setTransition(messageOptions.getTransition());
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.RGB_COLOR,
        packParam);
    logger.info(() -> String.format("Brightness :- Data: %s", Json.encode(pack)));
    send(pack, function);
  }

  public void sendTts(final String text, final Integer volume, final String voice,
      final Handler<Buffer> function) {
    final LumiaPackParam packParam = new LumiaPackParam().setValue(text).setVolume(volume)
        .setVoice(voice);
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.TTS, packParam);
    logger.info(() -> String.format("TTSing :- Data: %s", Json.encode(pack)));
    send(pack, function);
  }

}
