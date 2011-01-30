package de.bastian.clan.server;

import java.util.List;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

import de.bastian.clan.server.domain.PushClient;

public class PushServer {

    private static final String APP_PREFIX = "clan-";

    public static String createChannel(PushClient client) {
        return getChannelService().createChannel(generateKey(client));
    }

    public static void sendMessage(List<PushClient> clients, String msg) {
        for (PushClient client : clients) {
            try {
                getChannelService().sendMessage(new ChannelMessage(generateKey(client), msg));
            } catch (Exception e) {
                client.remove();
            }
        }
    }

    public static void sendMessageToAll(String msg) {
        sendMessage(PushClient.findAll(), msg);
    }

    private static final String generateKey(PushClient client) {
        return APP_PREFIX + client.getId();
    }

    private static ChannelService getChannelService() {
        return ChannelServiceFactory.getChannelService();
    }

}
