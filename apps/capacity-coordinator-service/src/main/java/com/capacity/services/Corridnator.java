package com.capacity.services;

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

    public List<ElevatorState> _inUse = Collections.synchronizedList(new ArrayList<ElevatorState>());

    public ElevatorResponse processRequest(ElevatorRequest request) {
        ElevatorResponse response = new ElevatorResponse();
        try {
            if (request.body != null) {
                try {
                    if(_inUse.size() == 0) {
                        //Grab an ElevatorState from the pool...
                        synchronized (this._inUse) {
                            ElevatorState elevator = this.acquireReusable();
                            elevator.currentDirection = request.body.direction;
                            for (Occupant occupant : request.body.occupantsEntering) {
                                elevator.addOccupant(occupant);
                            }
                        }
                    } else {
                        for (int count = 0; count < _inUse.size(); count++) {
                            if ((_inUse.get(count).currentFloor == request.body.currentFloor) && (_inUse.get(count).currentDirection == request.body.direction)) {
                                for (Occupant occupant : request.body.occupantsEntering) {
                                    _inUse.get(count).addOccupant(occupant);
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    throw new RuntimeException("Corridnator(), Error when getting ElevatorState from pool");
                }
            } else {
               // log.error("ReceiverBase.receiveMessage(), messagePayLoad is null for queue {}", queueName);
            }
        } catch (RuntimeException rtex) {
            throw rtex;
        } finally {

        }
        return response;
    }

    public ElevatorState acquireReusable() throws Exception {
        try {
            if (_inUse.size() != 0) {
                for (int count = 0; count < _inUse.size(); count++) {
                    if (!_inUse.get(count).inUse) {
                        _inUse.get(count).inUse = true;
                        return _inUse.get(count);
                    }
                }

                if (_inUse.size() < maxSizePoolSize) {
                    ElevatorState item = (ElevatorState) pooledTargetSource.getTarget();
                    item.inUse = true;
                    _inUse.add(item);
                    return item;
                } else {
                    log.warn("Corridnator.releaseReusable(), Max Pool size reached {} ", maxSizePoolSize);
                    throw new Exception("ElevatorState max pool size reached");
                }

            } else if (_inUse.size() < maxSizePoolSize) {
                ElevatorState item = (ElevatorState) pooledTargetSource.getTarget();
                item.inUse = true;
                _inUse.add(item);
                return item;
            }
        } catch (Exception ex) {
            log.error("Corridnator.acquireReusable(), Exception:", ex);
        }
        return null;
    }
}
