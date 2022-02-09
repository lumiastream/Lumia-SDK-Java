package com.lumiastream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.lumiastream.client.ConnectionOptions;
import com.lumiastream.client.Lumia;
import com.lumiastream.client.MessageOptions;
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
import io.vertx.core.http.HttpServerOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.junit5.VertxTestContext.ExecutionBlock;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class LumiaIntegrationTest {

  static Vertx runTestOnContext = Vertx.vertx();
  private static Lumia client;

  @BeforeAll
  public static void init() throws Throwable {
    final VertxTestContext vertxTestContext = new VertxTestContext();
    runTestOnContext.createHttpServer(new HttpServerOptions().setPort(39231))
        .webSocketHandler(event -> event.handler(event::write)).listen();

    client = Lumia
        .getInstance(
            new ConnectionOptions().setHost("127.0.0.1").setPort(39231).setName("lumia-java-sdk")
                .setToken("39231"));
    final Promise<Boolean> isWebsocketClosed = client.connect(false);
    isWebsocketClosed.future().onComplete(vertxTestContext.succeedingThenComplete());
    assertTrue(vertxTestContext.awaitCompletion(5, TimeUnit.SECONDS));
    assertFalse(client.getWebSocket().isClosed());
    if (vertxTestContext.failed()) {
      throw vertxTestContext.causeOfFailure();
    }
  }

  @AfterAll
  public static void testStop() throws Throwable {
    final CountDownLatch countDownLatch = new CountDownLatch(1);
    client.stop(new Handler<Buffer>() {
      @Override
      public void handle(Buffer event) {
        System.out.println(event.toJsonObject().encode());

      }
    });
    countDownLatch.await();
  }

  @Test
  public void testGetInfo() throws InterruptedException {
    client.getInfo(event -> {

    });

  }

  @Test
  public void testSend() throws InterruptedException {
    final LumiaPackParam lumiaPackParam = new LumiaPackParam()
        .setValue(LumiaAlertValue.TWITCH_FOLLOWER.getValue());
    client
        .send(new LumiaSendPack(LumiaExternalActivityCommandType.ALERT, lumiaPackParam), event -> {

        });
  }

  @Test
  public void testSendAlert() throws InterruptedException {
    client
        .sendAlert(LumiaAlertValue.TWITCH_FOLLOWER, event -> {

        });
  }

  @Test
  public void testSendBrightness() {
    client
        .sendBrightness(1, new MessageOptions(), event -> {

        });
  }

  @Test
  public void testSendChatbot() {
    client
        .sendChatBot(Platform.TWITCH, "ME!", event -> {

        });

  }

  @Test
  public void testSendColor() {
    client
        .sendColor(new Rgb(1, 2, 3), 4, new MessageOptions(), event -> {

        });
  }

  @Test
  public void testSendCommand() {
    client
        .sendCommand(LumiaExternalActivityCommandType.CHAT_COMMAND.getValue(), false, true,
            event -> {

            });
  }

  @Test
  public void testSendTts() {
    client.sendTts("we", 10, "dd", event -> {

    });
  }

  @Test
  public void testWebsocketNotConnected() {
    Vertx.vertx().createHttpServer(new HttpServerOptions().setPort(39233))
        .webSocketHandler(event -> event.handler(event::write)).listen();

    client = Lumia
        .getInstance(
            new ConnectionOptions().setHost("127.0.0.1").setPort(39230).setName("lumia-java-sdk")
                .setToken("39230"));
    assertEquals(client.getWebSocket(), null);
    client.getInfo(event -> {

    });
  }
}
