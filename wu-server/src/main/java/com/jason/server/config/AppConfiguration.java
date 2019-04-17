package com.jason.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/8/24 01:43
 * @since JDK 1.8
 */
@Component
public class AppConfiguration {

    @Value("${cim.server.port}")
    private int cimServerPort;

    @Value("${cim.heartbeat.time}")
    private long heartBeatTime ;
    
    public int getCimServerPort() {
        return cimServerPort;
    }

    public void setCimServerPort(int cimServerPort) {
        this.cimServerPort = cimServerPort;
    }

    public long getHeartBeatTime() {
        return heartBeatTime;
    }

    public void setHeartBeatTime(long heartBeatTime) {
        this.heartBeatTime = heartBeatTime;
    }
}
