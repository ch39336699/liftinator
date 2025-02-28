package com.capacity.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Slf4j
@Service("com.capacity.services.ElevatorState")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ElevatorState {

    public boolean inUse;

    public int runCount;
    public long lastRunTime;

    private static int RETRY_QUEUE = 0;
    private static int ERROR_QUEUE = 1;
    private static int ROLLBACK_QUEUE = 2;

}
