package com.edge.timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Profile({"none"})
@Component
public class Interval6HourTimer extends IntervalTimerImplBase {

    //@PostConstruct
    public void setup() {
    }

    @Scheduled(fixedDelay = ONE_HOUR * 6)   // every 6 hours
    public void runRules() {
    }

}
