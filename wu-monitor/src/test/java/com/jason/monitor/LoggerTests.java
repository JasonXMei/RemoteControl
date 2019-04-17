package com.jason.monitor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTests {

	private Logger logger = LoggerFactory.getLogger(LoggerTests.class);
	//trace < debug < info < warn < error
    @Test
    public void traceLog(){
    	logger.trace("{}", "trace");
    }
    
    @Test
    public void debugLog(){
    	logger.debug("{}", "debug");
    }
    
    @Test
    public void infoLog(){
    	logger.info("{}", "info");
    }
    
    @Test
    public void warnLog(){
    	logger.warn("{}", "warn");
    }
    
    @Test
    public void errorLog(){
    	logger.error("{}", "error");
    }
}
