package com.lumiastream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.lumiastream.client.Lumia;
import com.lumiastream.client.LumiaOptions;
import com.lumiastream.client.LumiaWebSocketClient;
import com.lumiastream.common.enums.LightBrand;
import com.lumiastream.common.enums.LumiaAlertValue;
import com.lumiastream.common.enums.LumiaExternalActivityCommandType;
import com.lumiastream.common.LumiaLight;
import com.lumiastream.common.LumiaPackParam;
import com.lumiastream.common.LumiaSendPack;
import com.lumiastream.common.enums.Platform;
import com.lumiastream.common.Rgb;
import com.sun.tools.javac.util.List;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LumiaIntegrationTest {

  private static LumiaWebSocketClient client;

  @BeforeAll
  public static void init() {
    Vertx.vertx().createHttpServer(new HttpServerOptions().setPort(39231))
        .webSocketHandler(event -> event.handler(event::write)).listen();

    client = Lumia.client(new LumiaOptions("127.0.0.1", 39231, "lumia-java-sdk", "39231"));
    final Boolean isWebsocketClosed = client.connect().await().indefinitely();
    assertFalse(isWebsocketClosed);
  }

  @AfterAll
  public static void testStop() throws InterruptedException {
    final CountDownLatch countDownLatch = new CountDownLatch(1);
    client.stop().subscribe().with(jsonObject -> {
      countDownLatch.countDown();
      System.out.println(jsonObject.encode());
    });
    countDownLatch.await();
  }

  @Test
  public void testGetInfo() throws InterruptedException {
    final CountDownLatch countDownLatch = new CountDownLatch(1);
    client.getInfo().subscribe().with(jsonObject -> {
      countDownLatch.countDown();
      System.out.println(jsonObject.encode());
    });
    countDownLatch.await();
  }

  @Test
  public void testSend() throws InterruptedException {
    final CountDownLatch countDownLatch = new CountDownLatch(1);
    final LumiaPackParam lumiaPackParam = new LumiaPackParam()
        .setValue(LumiaAlertValue.TWITCH_FOLLOWER.getValue());
    client.send(new LumiaSendPack(LumiaExternalActivityCommandType.ALERT, lumiaPackParam))
        .subscribe().with(jsonObject -> {
      countDownLatch.countDown();
      System.out.println(jsonObject.encode());
    });
    countDownLatch.await();
  }

  @Test
  public void testSendAlert() throws InterruptedException {
    final CountDownLatch countDownLatch = new CountDownLatch(1);
    client.sendAlert(LumiaAlertValue.TWITCH_FOLLOWER)
        .subscribe().with(jsonObject -> {
      countDownLatch.countDown();
      System.out.println(jsonObject.encode());
    });
    countDownLatch.await();
  }

  @Test
  public void testSendBrightness() {
    client.sendBrightness(1, Duration.ofDays(1), true)
        .subscribe().with(jsonObject -> System.out.println(jsonObject.encode()));
  }

  @Test
  public void testSendChatbot() {
    client.sendChatBot(Platform.TWITCH, "ME!")
        .subscribe().with(jsonObject -> {
      System.out.println(jsonObject.encode());
    });
  }

  @Test
  public void testSendColor() {
    client.sendColor(new Rgb(1, 2, 3), 4, Duration.ofNanos(1), Duration.ofMillis(1), true, false,
        List.of(new LumiaLight(LightBrand.COLOLIGHT, LightBrand.COLOLIGHT.getId())))
        .subscribe().with(jsonObject -> System.out.println(jsonObject.encode()));
  }

  @Test
  public void testSendCommand() {
    client.sendCommand(LumiaExternalActivityCommandType.CHAT_COMMAND, false, true)
        .subscribe().with(jsonObject -> {

      System.out.println(jsonObject.encode());
    });
  }

  @Test
  public void testSendTts() {
    client.sendTts("we", 10, "dd")
        .subscribe().with(jsonObject -> System.out.println(jsonObject.encode()));
  }

  @Test
  public void testWebsocketNotConnected() {
    Vertx.vertx().createHttpServer(new HttpServerOptions().setPort(39233))
        .webSocketHandler(event -> event.handler(event::write)).listen();

    client = Lumia.client(new LumiaOptions("127.0.0.1", 39230, "lumia-java-sdk", "39230"));
    assertEquals(client.getWebSocket(), null);
    client.getInfo().subscribe().with(jsonObject -> assertEquals(jsonObject.getString("message"),"Websocket not connected"));
  }
}
