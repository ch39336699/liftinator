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

    // Execute every 3 seconds to simulate moving between floors.
    //Run update on each active elevator to update state.
    @Scheduled(fixedDelay = 3000)
    public void job() {
        if(corridnator.elevatorsInUse.size() > 0) {
            for (int count = 0; count < corridnator.elevatorsInUse.size(); count++) {
                    corridnator.elevatorsInUse.get(count).update();
            }
        }
    }

}
