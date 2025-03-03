package com.capacity.timer;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

/*
 * Project: liftinator
 * File: IntervalTimerImplBase.java
 * Author: Chris Harper
 * The IntervalTimerImplBase class defines some basic constants and a setup method,
 * potentially for use as a base class in a larger timer or scheduling system.
 */
@Slf4j
@Component
public class IntervalTimerImplBase {

    protected static final long ONE_HOUR = 60 * 60 * 1000;
    protected static final long ONE_DAY = 24 * 60 * 60 * 1000;

    /**
     * Initialization routine
     */
    //@PostConstruct
    public void setup() {
    }

}
