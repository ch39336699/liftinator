package com.capacity.services;

//import com.capacity.model.ElevatorResponse;
import com.common.model.ElevatorRequest;
import com.common.model.ElevatorResponse;
import com.common.model.Occupant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.target.CommonsPool2TargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class Corridnator {

    @Autowired
    @Qualifier("ElevatorStateTargetPool")
    CommonsPool2TargetSource pooledTargetSource;

    int maxSizePoolSize = 4;

    public List<ElevatorState> _available = Collections.synchronizedList(new ArrayList<ElevatorState>());

    public ElevatorResponse processRequest(ElevatorRequest request) {
        ElevatorResponse response = new ElevatorResponse();
        try {
            if (request.body != null) {
                try {
                    if(_available.size() == 0) {
                        //Grab a ElevatorState from the pool...
                        synchronized (this._available) {
                            ElevatorState elevator = this.acquireReusable();
                            for (Occupant occupant : request.body.occupantsEntering) {
                                elevator.addOccupant(occupant);
                            }
                        }
                    } else {
                        // Elevators are in flight. So check location/capacity of running elevators compared to
                        // new occupants waiting.
                    }
                } catch (Exception ex) {
                   // log.error("ReceiverBase.receiveMessage(), Error when getting BaseMDB from pool for queue {}. Pool Active: {}, Pool Max: {}, Exception:", queueName, this._available.size(), this.rulesProcMaxSize, ex);
                    throw new RuntimeException("ReceiverBase(), Error when getting BaseMDB from pool");
                }
            } else {
               // log.error("ReceiverBase.receiveMessage(), messagePayLoad is null for queue {}", queueName);
            }
        } catch (RuntimeException rtex) {
            //Catch and throw back to JMS to put messages back on the queue.
           // log.warn("ReceiverBase.receiveMessage(), Rolling message back to JMS queue: {}. RuntimeException:", queueName, ExceptionUtils.getStackTrace(rtex));
            throw rtex;
        } finally {

        }
        return response;
    }

    public ElevatorState acquireReusable() throws Exception {
        try {
            if (_available.size() != 0) {
                for (int count = 0; count < _available.size(); count++) {
                    if (!_available.get(count).inUse) {
                        _available.get(count).inUse = true;
                        return _available.get(count);
                    }
                }

                if (_available.size() < maxSizePoolSize) {
                    ElevatorState item = (ElevatorState) pooledTargetSource.getTarget();
                    item.inUse = true;
                    _available.add(item);
                    return item;
                } else {
                    log.warn("Corridnator.releaseReusable(), Max Pool size reached {} ", maxSizePoolSize);
                    throw new Exception("ElevatorState max pool size reached");
                }

            } else if (_available.size() < maxSizePoolSize) {
                ElevatorState item = (ElevatorState) pooledTargetSource.getTarget();
                item.inUse = true;
                _available.add(item);
                return item;
            }
        } catch (Exception ex) {
            log.error("Corridnator.acquireReusable(), Exception:", ex);
        }
        return null;
    }
}
