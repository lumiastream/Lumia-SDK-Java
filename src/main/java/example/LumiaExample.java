///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.lumiastream:lumiastream-websocket-sdk:0.1.0-SNAPSHOT

import java.time.Duration;

import com.lumiastream.client.Lumia;
import com.lumiastream.client.LumiaOptions;
import com.lumiastream.client.LumiaWebSocketClient;
import com.lumiastream.common.LumiaAlertValue;
import com.lumiastream.common.LumiaCommandType;
import com.lumiastream.common.LumiaPackParam;
import com.lumiastream.common.LumiaSendPack;
import com.lumiastream.common.Platform;

public class LumiaExample {

  public static void main(String... args) {
    final LumiaWebSocketClient client = Lumia
        .client(new LumiaOptions("127.0.0.1", 39231, "lumia-java-sdk", "insert-token"));
    client.connect().await().indefinitely();
    client.getInfo().subscribe().with(System.out::println);

    // Send an alert
    client.sendAlert(LumiaAlertValue.TWITCH_FOLLOWER).subscribe().with(System.out::println);

    // Sending a command
    client.sendCommand("red", false, false).subscribe().with(System.out::println);

    // Sending a command
    client.sendCommand("red", false, false).subscribe().with(System.out::println);

    // Sending a basic color

    // Sending a brightness
    // client.sendBrightness(100, Duration(0),
    // false).subscribe().with(System.out::println);

    // Sending a TTS message
    client.sendTts("This SDK is the best", 100, "").subscribe().with(System.out::println);

    // Sending a Chat bot message
    client.sendChatBot(Platform.TWITCH, "This SDK is the best").subscribe().with(System.out::println);

    // Sending a raw event example
    final LumiaPackParam lumiaPackParam = new LumiaPackParam();
    lumiaPackParam.setValue(LumiaAlertValue.TWITCH_FOLLOWER.getValue());
    final LumiaSendPack lumiaSendPack = new LumiaSendPack(LumiaCommandType.ALERT,
        lumiaPackParam);
    client.send(lumiaSendPack).subscribe().with(System.out::println);

    client.getWebSocket().handler(buffer -> System.out.println(buffer));

  }
}
