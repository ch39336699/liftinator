package com.capacity.timer;

import com.capacity.services.Corridnator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class Interval5MinTimer extends IntervalTimerImplBase {

    @Autowired
    Corridnator corridnator;

    @Scheduled(fixedDelay = 3000) // Execute every 3 seconds to simulate moving between floors
    public void job() {
        if(corridnator._inUse.size() > 0) {
            for (int count = 0; count < corridnator._inUse.size(); count++) {
                    corridnator._inUse.get(count).update();
            }
        }
    }

}
