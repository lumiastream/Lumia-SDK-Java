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
import java.time.Duration;

public class LumiaExample {

  public static void main(String... args) {
    final Lumia client = Lumia
        .getInstance(
            new ConnectionOptions().setHost("127.0.0.1").setPort(39231).setName("lumia-java-sdk")
                .setToken("insert-token"));
    client.connect().await().indefinitely();
    client.getInfo().subscribe().with(System.out::println);

    // Send an alert
    client.sendAlert(LumiaAlertValue.TWITCH_FOLLOWER).subscribe().with(System.out::println);

    // Sending a command
    client.sendCommand("red", false, false).subscribe().with(System.out::println);

    // Sending a command
    client.sendCommand("red", false, false).subscribe().with(System.out::println);

    // Sending a basic color
    final MessageOptions messageOptions = new MessageOptions().setDuration(Duration.ofMillis(1000));
    client.sendColor(new Rgb(255, 0, 0), 60, messageOptions);

    // Sending a brightness
    client.sendBrightness(100, new MessageOptions()).subscribe().with(System.out::println);

    // Sending a TTS message
    client.sendTts("This SDK is the best", 100, "").subscribe().with(System.out::println);

    // Sending a Chat bot message
    client.sendChatBot(Platform.TWITCH, "This SDK is the best").subscribe()
        .with(System.out::println);

    // Sending a raw event example
    final LumiaPackParam lumiaPackParam = new LumiaPackParam();
    lumiaPackParam.setValue(LumiaAlertValue.TWITCH_FOLLOWER.getValue());
    final LumiaSendPack lumiaSendPack = new LumiaSendPack(LumiaExternalActivityCommandType.ALERT,
        lumiaPackParam);
    client.send(lumiaSendPack).subscribe().with(System.out::println);

    client.getWebSocket().handler(buffer -> System.out.println(buffer));

  }
}
