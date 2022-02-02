///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.lumiastream:lumiastream-websocket-sdk:0.1.0-SNAPSHOT

import com.lumiastream.client.ConnectionOptions;
import com.lumiastream.client.Lumia;
import com.lumiastream.client.MessageOptions;
import com.lumiastream.common.LumiaPackParam;
import com.lumiastream.common.LumiaSendPack;
import com.lumiastream.common.Rgb;
import com.lumiastream.common.enums.LumiaAlertValue;
import com.lumiastream.common.enums.LumiaExternalActivityCommandType;
import com.lumiastream.common.enums.Platform;
import io.vertx.mutiny.core.buffer.Buffer;
import java.time.Duration;
import java.util.logging.Logger;

public class LumiaExample {

  private static final Logger logger = Logger.
      getLogger(LumiaExample.class.getCanonicalName());

  public static void main(String... args) {
    final Lumia client = Lumia
        .getInstance(
            new ConnectionOptions().setHost("127.0.0.1").setPort(39231).setName("lumia-java-sdk")
                .setToken("insert-token"));
    client.connect().await().indefinitely();
    client.getInfo().subscribe().with(jsonObject -> logger.info(jsonObject::encode));

    // Send an alert
    client.sendAlert(LumiaAlertValue.TWITCH_FOLLOWER).subscribe()
        .with(jsonObject -> logger.info(jsonObject::encode));

    // Sending a command
    client.sendCommand("red", false, false).subscribe()
        .with(jsonObject -> logger.info(jsonObject::encode));

    // Sending a command
    client.sendCommand("red", false, false).subscribe()
        .with(jsonObject -> logger.info(jsonObject::encode));

    // Sending a basic color
    final MessageOptions messageOptions = new MessageOptions().setDuration(Duration.ofMillis(1000));
    client.sendColor(new Rgb(255, 0, 0), 60, messageOptions);

    // Sending a brightness
    client.sendBrightness(100, new MessageOptions()).subscribe()
        .with(jsonObject -> logger.info(jsonObject::encode));

    // Sending a TTS message
    client.sendTts("This SDK is the best", 100, "").subscribe()
        .with(jsonObject -> logger.info(jsonObject::encode));

    // Sending a Chat bot message
    client.sendChatBot(Platform.TWITCH, "This SDK is the best").subscribe()
        .with(jsonObject -> logger.info(jsonObject::encode));

    // Sending a raw event example
    final LumiaPackParam lumiaPackParam = new LumiaPackParam();
    lumiaPackParam.setValue(LumiaAlertValue.TWITCH_FOLLOWER.getValue());
    final LumiaSendPack lumiaSendPack = new LumiaSendPack(LumiaExternalActivityCommandType.ALERT,
        lumiaPackParam);
    client.send(lumiaSendPack).subscribe().with(jsonObject -> logger.info(jsonObject::encode));

    // listen to events using the websocket handler directly
    client.getWebSocket().handler(buffer -> {
      final String type = buffer.toJsonObject().getString("type");
      output(buffer, type);
    });

    // listen to events using Mutiny Multi API
    client.getWebSocket().toMulti().subscribe().with(buffer -> {
      final String type = buffer.toJsonObject().getString("type");
      output(buffer, type);
    });
  }

  private static void output(Buffer buffer, String type) {
    switch (type) {
      case "states":
        logger.info(
            () -> String.format("States have been updated: %s", buffer.toJsonObject().encode()));
        break;
      case "alert":
        logger.info(() -> String.format("New alert: %s", buffer.toJsonObject().encode()));
        break;
      case "command":
        logger.info(() -> String
            .format("A Chat Command is being triggered: %s", buffer.toJsonObject().encode()));
        break;
      case "chat":
        logger.info(() -> String.format("New chat message: %s", buffer.toJsonObject().encode()));
        break;
      default:
        logger.info(() -> "NO OP");
    }
  }
}
