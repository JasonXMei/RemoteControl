package com.jason.web.handler;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SocketHandler {
    private static final Map<Integer, NioSocketChannel> CHANNEL_MAP_USE = new ConcurrentHashMap<>(3000);
    private static final Map<Integer, NioSocketChannel> CHANNEL_MAP_CLIENT = new ConcurrentHashMap<>(3000);

    public static void putUse(Integer id, NioSocketChannel socketChannel) {
        CHANNEL_MAP_USE.put(id, socketChannel);
    }

    public static NioSocketChannel getUse(Integer id) {
        return CHANNEL_MAP_USE.get(id);
    }

    public static void removeUse(Integer userId) {
        NioSocketChannel nioSocketChannel = CHANNEL_MAP_USE.get(userId);
        if(nioSocketChannel != null){
            nioSocketChannel.close();
        }
        CHANNEL_MAP_USE.remove(userId);
    }

    public static int removeUse(NioSocketChannel nioSocketChannel) {
        return removeMap(CHANNEL_MAP_USE, nioSocketChannel);
    }

    public static void putClient(Integer id, NioSocketChannel socketChannel) {
        CHANNEL_MAP_CLIENT.put(id, socketChannel);
    }

    public static NioSocketChannel getClient(Integer id) {
        return CHANNEL_MAP_CLIENT.get(id);
    }

    public static void removeClient(Integer userId) {
        NioSocketChannel nioSocketChannel = CHANNEL_MAP_CLIENT.get(userId);
        if(nioSocketChannel != null){
            nioSocketChannel.close();
        }
        CHANNEL_MAP_CLIENT.remove(userId);
    }

    public static int removeClient(NioSocketChannel nioSocketChannel) {
        return removeMap(CHANNEL_MAP_CLIENT, nioSocketChannel);
    }

    private static int removeMap(Map<Integer, NioSocketChannel> map, NioSocketChannel nioSocketChannel){
        int userId = 0;
        for(Map.Entry<Integer, NioSocketChannel> entry: map.entrySet()){
            if(entry.getValue() == nioSocketChannel){
                userId = entry.getKey();
                map.remove(userId);
                break;
            }
        }
        return userId;
    }
}
