package com.capacity.timer;

import com.capacity.services.Corridnator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
 * Project: liftinator
 * File: Interval3MinTimer.java
 * Author: Chris Harper
 * The Interval3MinTimer class extends the IntervalTimerImplBase class and defines a scheduled task that runs every 3 seconds.
 * The class simulates the movement of elevators between floors and updates the state of active elevators
 * by invoking the update() method on them.
 */
@Slf4j
@Component
public class Interval3MinTimer extends IntervalTimerImplBase {

    @Autowired
    Corridnator corridnator;

    //Execute every 3 seconds to simulate moving between floors.
    //Run update on each active elevator to update state.
    @Scheduled(fixedDelay = 3000)
    public void job() {
        if(corridnator.elevatorsInUse.size() > 0) {
            for (int count = 0; count < corridnator.elevatorsInUse.size(); count++) {
                if(!corridnator.elevatorsInUse.get(count).stopped) {
                    corridnator.elevatorsInUse.get(count).update();
                }
            }
        }
    }

}
