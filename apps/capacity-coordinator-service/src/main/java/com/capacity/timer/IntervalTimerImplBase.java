package com.capacity.timer;


import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

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
