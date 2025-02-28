package com.capacity.timer;
 

/**
 * Copyright (c) 2004 FDX Corporation.  All Rights Reserved.
*/

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//import javax.annotation.PostConstruct;

@Slf4j
@Profile({"none"})
@Component
public class Interval24HourTimer extends IntervalTimerImplBase {

   // @Autowired
   // private XLTNormalJMSPublisher xltPublisher;

    @Value("${rules24Hours : ProcessXLTShipmentExpirationTimer}")
    private String[] rules;

   // @PostConstruct
    public void setup() {
    }

	@Scheduled(fixedDelay = ONE_DAY)   // every 24 hours
	public void runRules() {
      //  putRulesOnExternalJMSQueueWithoutProperties(rules, xltPublisher);
	}

}
