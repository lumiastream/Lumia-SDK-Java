///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.lumiastream:lumiastream-websocket-sdk:0.1.0-SNAPSHOT

import com.lumiastream.client.Lumia;
import com.lumiastream.client.LumiaOptions;
import com.lumiastream.client.LumiaWebSocketClient;
import com.lumiastream.common.LumiaAlertValue;
import com.lumiastream.common.LumiaCommandType;
import com.lumiastream.common.LumiaPackParam;
import com.lumiastream.common.LumiaSendPack;

public class LumiaExample {

  public static void main(String... args) {
    final LumiaWebSocketClient client = Lumia.client(new LumiaOptions("127.0.0.1", 39231,"lumia-java-sdk","39231"));
    client.connect().await().indefinitely();
    client.getInfo().subscribe().with(System.out::println);

    final LumiaPackParam lumiaPackParam = new LumiaPackParam();
    lumiaPackParam.setValue(LumiaAlertValue.TWITCH_FOLLOWER.getValue());
    final LumiaSendPack lumiaSendPack = new LumiaSendPack(LumiaCommandType.ALERT,
        lumiaPackParam);

    client.send(lumiaSendPack).subscribe().with(System.out::println);

    client.getWebSocket().handler(buffer -> System.out.println(buffer));

  }
}
