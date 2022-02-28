///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.lumiastream:lumiastream-websocket-sdk:0.1.0-SNAPSHOT

import com.lumiastream.client.ConnectionOptions;
import com.lumiastream.client.Lumia;
import com.lumiastream.client.MessageOptions;
import com.lumiastream.common.LumiaPackParam;
import com.lumiastream.common.LumiaSendPack;
import com.lumiastream.common.Rgb;
import com.lumiastream.common.enums.LumiaAlertValue;
import com.lumiastream.common.enums.LumiaEventType;
import com.lumiastream.common.enums.LumiaExternalActivityCommandType;
import com.lumiastream.common.enums.Platform;
import io.vertx.core.buffer.Buffer;
import java.time.Duration;
import java.util.logging.Logger;

public class GamesGlowExample {

  private static final Logger logger = Logger.getLogger(GamesGlowExample.class.getCanonicalName());

  public static void main(String... args) {
    final Lumia client = Lumia
        .getInstance(
            new ConnectionOptions().setHost("127.0.0.1").setPort(39231).setName("lumia-java-sdk")
                .setToken("insert-token-here"));
    client.connect(true).future().onSuccess(connectedStatus -> {
      System.out.println("WebSocket closed status: " + connectedStatus);

      // Sending a games glow
      // client.sendGamesGlowAlert("minecraft_lumia__", false, false,
      // data -> System.out.println("COMMAND: " + data.toJsonObject()));

      // listen to events using the websocket handler directly
      client.getWebSocket().handler(buffer -> {
        System.out.println(buffer.toJsonObject().encode());
        final String type = buffer.toJsonObject().getString("type");
        if (type != null) {
          output(buffer, type);
        }
      }).closeHandler(exEvent -> System.out.println(exEvent));
    }).onFailure(failureEvent -> failureEvent.printStackTrace());
  }

  private static void output(Buffer buffer, String type) {
    switch (type) {
      case "chat":
        logger.info(
            () -> String.format("New chat message: %s", buffer.toJsonObject().encode()));
        break;
      case "gamesglow_command":
        logger.info(
            () -> String.format("Games Glow Command: %s", buffer.toJsonObject().encode()));
        break;
      case "gamesglow_virtuallight":
        logger.info(
            () -> String.format("Games Glow Command: %s", buffer.toJsonObject().encode()));
        break;
      default:
        logger.info(() -> "NO OP");
    }
  }
}
