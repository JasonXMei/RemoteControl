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

    public static Integer removeUse(NioSocketChannel nioSocketChannel) {
        return removeMap(CHANNEL_MAP_USE, nioSocketChannel);
    }

    public static void putClient(Integer id, NioSocketChannel socketChannel) {
        CHANNEL_MAP_CLIENT.put(id, socketChannel);
    }

    public static NioSocketChannel getClient(Integer id) {
        return CHANNEL_MAP_CLIENT.get(id);
    }

    public static Integer removeClient(NioSocketChannel nioSocketChannel) {
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
