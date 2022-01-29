package com.lumiastream;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.lumiastream.client.Lumia;
import com.lumiastream.client.LumiaWebSocketClient;
import com.lumiastream.common.LightBrand;
import com.lumiastream.common.LumiaAlertValue;
import com.lumiastream.common.LumiaCommandType;
import com.lumiastream.common.LumiaLight;
import com.lumiastream.common.LumiaPackParam;
import com.lumiastream.common.LumiaSendPack;
import com.lumiastream.common.Platform;
import com.lumiastream.common.Rgb;
import com.sun.tools.javac.util.List;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LumiaTest {

  private static LumiaWebSocketClient client;

  @BeforeAll
  public static void init() {
    client = Lumia.client();
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
    client.getWebSocket().close();
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
    final LumiaPackParam lumiaPackParam = new LumiaPackParam();
    lumiaPackParam.setValue(LumiaAlertValue.TWITCH_FOLLOWER.getValue());
    client.send(new LumiaSendPack(LumiaCommandType.ALERT, lumiaPackParam))
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
        .subscribe().with(jsonObject -> {
      
      System.out.println(jsonObject.encode());
    });
  }

  @Test
  public void testSendChatbot() {
    client.sendChatBot(Platform.TWITCH, "ME!")
        .subscribe().with(jsonObject -> {
      
      {
        
        System.out.println(jsonObject.encode());
      }
      ;
    });
  }

  @Test
  public void testSendColor() {
    client.sendColor(new Rgb(1, 2, 3), 4, Duration.ofNanos(1), Duration.ofMillis(1), true, false,
        List.of(new LumiaLight(LightBrand.COLOLIGHT, LightBrand.COLOLIGHT.getId())))
        .subscribe().with(jsonObject -> {
      
      System.out.println(jsonObject.encode());
    });
  }

  @Test
  public void testSendCommand() {
    client.sendCommand(LumiaCommandType.CHAT_COMMAND, false, true)
        .subscribe().with(jsonObject -> {
      
      System.out.println(jsonObject.encode());
    });
  }

  @Test
  public void testSendTts() {
    client.sendTts("we", 10, "dd")
        .subscribe().with(jsonObject -> {
      
      System.out.println(jsonObject.encode());
    });
  }
}
