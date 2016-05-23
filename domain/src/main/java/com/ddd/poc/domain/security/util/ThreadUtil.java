package com.ddd.poc.domain.security.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class ThreadUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(ThreadUtil.class);

    public static void simulateLatecy() {
        try {
            int latency = new Random().nextInt(100);
            LOGGER.info("simulated latency: {}ms", latency);
            Thread.sleep(latency);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
