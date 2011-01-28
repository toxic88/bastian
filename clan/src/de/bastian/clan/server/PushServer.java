package de.bastian.clan.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

import de.bastian.clan.server.domain.PushClient;
import de.bastian.clan.server.domain.User;

public class PushServer {

    public static String createChannel(String name) {
        return getChannelService().createChannel(name + "" + new Date().getTime());
    }

    public static String createChannel(User user) {
        return createChannel("" + user.getId());
    }

    public static void sendMessage(List<String> clients, String msg) {
        for (String client : clients) {
            try {
                getChannelService().sendMessage(new ChannelMessage(client, msg));
            } catch (Exception e) {

            }
        }
    }

    public static void sendMessageToAll(String msg) {
        List<PushClient> clients = PushClient.findAll();
        ArrayList<String> clientIds = new ArrayList<String>();

        for (PushClient client : clients) {
            clientIds.add(client.getClientId());
        }

        sendMessage(clientIds, msg);
    }

    private static ChannelService getChannelService() {
        return ChannelServiceFactory.getChannelService();
    }

}
