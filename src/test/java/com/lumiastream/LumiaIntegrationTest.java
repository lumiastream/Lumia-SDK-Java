package com.lumiastream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.lumiastream.client.ConnectionOptions;
import com.lumiastream.client.Lumia;
import com.lumiastream.client.MessageOptions;
import com.lumiastream.common.LumiaPackParam;
import com.lumiastream.common.LumiaSendPack;
import com.lumiastream.common.Rgb;
import com.lumiastream.common.enums.LumiaAlertValue;
import com.lumiastream.common.enums.LumiaExternalActivityCommandType;
import com.lumiastream.common.enums.Platform;
import io.smallrye.mutiny.helpers.test.AssertSubscriber;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LumiaIntegrationTest {

  private static Lumia client;

  @BeforeAll
  public static void init() {
    Vertx.vertx().createHttpServer(new HttpServerOptions().setPort(39231))
        .webSocketHandler(event -> event.handler(event::write)).listen();

    client = Lumia
        .getInstance(
            new ConnectionOptions().setHost("127.0.0.1").setPort(39231).setName("lumia-java-sdk")
                .setToken("39231"));
    final Boolean isWebsocketClosed = client.connect(false).await().atMost(Duration.ofMinutes(1));
    assertFalse(isWebsocketClosed);
  }

  @AfterAll
  public static void testStop() throws InterruptedException {
//    final CountDownLatch countDownLatch = new CountDownLatch(1);
    final AssertSubscriber<JsonObject> jsonObjectAssertSubscriber = client.stop().subscribe()
        .withSubscriber(AssertSubscriber.create(1));

        jsonObjectAssertSubscriber.assertCompleted().assertItems(new JsonObject().put("message","WebSocket not connected"));
  }

  @Test
  public void testGetInfo() throws InterruptedException {
    final AssertSubscriber<JsonObject> jsonObjectAssertSubscriber = client.getInfo().subscribe()
        .withSubscriber(AssertSubscriber.create(1));
    jsonObjectAssertSubscriber.assertCompleted();

  }

  @Test
  public void testSend() throws InterruptedException {
    final LumiaPackParam lumiaPackParam = new LumiaPackParam()
        .setValue(LumiaAlertValue.TWITCH_FOLLOWER.getValue());
    final AssertSubscriber<JsonObject> jsonObjectAssertSubscriber = client
        .send(new LumiaSendPack(LumiaExternalActivityCommandType.ALERT, lumiaPackParam))
        .subscribe().withSubscriber(AssertSubscriber.create(1));
    jsonObjectAssertSubscriber.assertCompleted();
  }

  @Test
  public void testSendAlert() throws InterruptedException {
    final CountDownLatch countDownLatch = new CountDownLatch(1);
    final AssertSubscriber<JsonObject> jsonObjectAssertSubscriber = client
        .sendAlert(LumiaAlertValue.TWITCH_FOLLOWER)
        .subscribe().withSubscriber(AssertSubscriber.create(1));
    jsonObjectAssertSubscriber.assertCompleted();
  }

  @Test
  public void testSendBrightness() {
    final AssertSubscriber<JsonObject> jsonObjectAssertSubscriber = client
        .sendBrightness(1, new MessageOptions())
        .subscribe().withSubscriber(AssertSubscriber.create(1));
    jsonObjectAssertSubscriber.assertCompleted();
  }

  @Test
  public void testSendChatbot() {
    final AssertSubscriber<JsonObject> jsonObjectAssertSubscriber = client
        .sendChatBot(Platform.TWITCH, "ME!")
        .subscribe().withSubscriber(AssertSubscriber.create(1));
    jsonObjectAssertSubscriber.assertCompleted();

  }

  @Test
  public void testSendColor() {
    final AssertSubscriber<JsonObject> jsonObjectAssertSubscriber = client
        .sendColor(new Rgb(1, 2, 3), 4, new MessageOptions())
        .subscribe().withSubscriber(AssertSubscriber.create(1));
    jsonObjectAssertSubscriber.assertCompleted();
  }

  @Test
  public void testSendCommand() {
    final AssertSubscriber<JsonObject> jsonObjectAssertSubscriber = client
        .sendCommand(LumiaExternalActivityCommandType.CHAT_COMMAND.getValue(), false, true)
        .subscribe().withSubscriber(AssertSubscriber.create(1));
    jsonObjectAssertSubscriber.assertCompleted();
  }

  @Test
  public void testSendTts() {
    final AssertSubscriber<JsonObject> jsonObjectAssertSubscriber = client.sendTts("we", 10, "dd")
        .subscribe().withSubscriber(AssertSubscriber.create(1));
    jsonObjectAssertSubscriber.assertCompleted();
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
    client.getInfo().subscribe().with(
        jsonObject -> assertEquals(jsonObject.getString("message"), "WebSocket not connected"));
  }
}
