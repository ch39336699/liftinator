package com.capacity.timer;


/**
 * Copyright (c) 2004 FDX Corporation.  All Rights Reserved.
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Profile({"none"})
@Component
public class Interval5MinTimer extends IntervalTimerImplBase {

    private static final long INTERVAL = 1000 * 60 * 2; // 5 min

   // @PostConstruct
    public void setup() {
    }

    @Scheduled(fixedDelay = INTERVAL)
    public void runRules() {
       // publishToXLT();
    }

}
