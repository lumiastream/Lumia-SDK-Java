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

  final Vertx vertx = Vertx.vertx();
  HashMap<Integer, Handler<Buffer>> hashMap = new HashMap<>();
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

    vertx.createHttpClient()
        .webSocket(lumiaOptions.getPort(), lumiaOptions.getHost(), uri.toString())
        .onSuccess(successEvent -> {
          webSocket = successEvent;
          webSocket
              .closeHandler(closeEvent -> connect(shouldAutoReconnect));
          result.complete(successEvent.isClosed());
        })
        .onFailure(failureEvent -> {
          failureEvent.printStackTrace();
          logger.info(() -> "Reconnecting ...\n");
          connect(true);
          result.complete(true);
        });
    return result;
  }

  public void getInfo(final Handler<Buffer> handler) {
    final JsonObject getInfoPayload = new JsonObject().put("retrieve", true)
        .put("method", "retrieve");
    final Buffer buffer = Buffer.buffer(getInfoPayload.toString());
    logger.info(() -> String.format("Getting Info:- Data: %s", buffer));
    sendWebSocketMessage(buffer.toJsonObject(), handler);
  }

  public void stop(
      final Handler<Buffer> handler) {
    final JsonObject getInfoPayload = new JsonObject().put("method", "stop");
    final Buffer buffer = Buffer.buffer(getInfoPayload.toString());
    logger.info(() -> String.format("Stopping:- Data: %s", buffer.toString()));
    sendWebSocketMessage(buffer.toJsonObject(),
        handler);
  }

  private void sendWebSocketMessage(final JsonObject json, final Handler<Buffer> handler) {
    final int context = handler.hashCode();
    hashMap.put(context, handler);
    json.put("context", context);
    if (webSocket != null) {
      logger.info(() -> String.format("Sending WebSocket Message:- Data: %s: WebSocket Closed: %s"
          , json, webSocket.isClosed()));
      webSocket.handler(event -> {
        final JsonObject entries = event.toJsonObject();
        final Integer context1 = entries.getInteger("context");
        if (context1 == null) {
          logger.warning(() -> String
              .format("`context` is absent from server message:- Data: %s: WebSocket Closed: %s"
                  , entries.encode(), webSocket.isClosed()));
        } else {
          final Handler<Buffer> bufferHandler = hashMap.get(context1);
          if (bufferHandler == null) {
            logger.warning(() -> String
                .format(
                    "No handler registered for this context:- context: %s: WebSocket Closed: %s"
                    , context1, webSocket.isClosed()));
          } else {
            bufferHandler.handle(event);
            hashMap.remove(context1);
          }
        }
      }).write(json.toBuffer());
    }
  }

  public void send(final LumiaSendPack pack, final Handler<Buffer> handler) {

    final String packString = Json.encode(pack);
    final JsonObject message = new JsonObject().put("lsorigin", "lumia-sdk");
    final JsonObject merged = message.mergeIn(new JsonObject(packString));
    logger.info(() -> String.format("Data: %s", merged.encode()));
    sendWebSocketMessage(merged, handler);
  }

  public void sendAlert(final LumiaAlertValue alert, final Handler<Buffer> handler) {
    final LumiaPackParam packParam = new LumiaPackParam().setValue(alert.getValue());
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.ALERT, packParam);
    logger.info(() -> String.format("Alerting :- Data: %s", Json.encode(pack)));
    send(pack, handler);
  }

  public void sendChatBot(final Platform platform, final String text,
      final Handler<Buffer> handler) {
    final LumiaPackParam packParam = new LumiaPackParam().setValue(text).setPlatform(platform);
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.CHATBOT_MESSAGE,
        packParam);
    logger.info(() -> String.format("Chat Botting :- Data: %s", Json.encode(pack)));
    send(pack, handler);
  }

  public void sendColor(final Rgb rgb, final Integer brightness,
      final MessageOptions messageOptions, final Handler<Buffer> handler) {
    final LumiaPackParam packParam = new LumiaPackParam().setValue(Json.encode(rgb))
        .setBrightness(brightness).setHold(messageOptions.getHoldDefault());
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.RGB_COLOR,
        packParam);
    logger.info(() -> String.format("Coloring :- Data: %s", Json.encode(pack)));
    send(pack, handler);
  }

  public void sendCommand(final String command, final Boolean hold,
      final Boolean skipQueue, final Handler<Buffer> handler) {
    final LumiaPackParam packParam = new LumiaPackParam().setValue(command).setHold(hold)
        .setSkipQueue(skipQueue);
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.CHAT_COMMAND,
        packParam);
    logger.info(() -> String.format("Commanding :- Data: %s", Json.encode(pack)));
    send(pack, handler);
  }

  public void sendBrightness(final Integer brightness,
      final MessageOptions messageOptions, final Handler<Buffer> handler) {
    final LumiaPackParam packParam = new LumiaPackParam().setBrightness(brightness)
        .setDuration(messageOptions.getDuration()).setTransition(messageOptions.getTransition());
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.RGB_COLOR,
        packParam);
    logger.info(() -> String.format("Brightness :- Data: %s", Json.encode(pack)));
    send(pack, handler);
  }

  public void sendTts(final String text, final Integer volume, final String voice,
      final Handler<Buffer> handler) {
    final LumiaPackParam packParam = new LumiaPackParam().setValue(text).setVolume(volume)
        .setVoice(voice);
    final LumiaSendPack pack = new LumiaSendPack(LumiaExternalActivityCommandType.TTS, packParam);
    logger.info(() -> String.format("TTSing :- Data: %s", Json.encode(pack)));
    send(pack, handler);
  }

}
