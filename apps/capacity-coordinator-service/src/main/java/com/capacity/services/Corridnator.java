package com.capacity.services;

import com.common.model.ElevatorRequest;
import com.common.model.ElevatorResponse;
import com.common.model.Occupant;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.target.CommonsPool2TargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

@Slf4j
@Service
public class Corridnator {

    @Autowired
    @Qualifier("ElevatorStateTargetPool")
    CommonsPool2TargetSource pooledTargetSource;

    int maxSizePoolSize = 4;

    public List<ElevatorState> elevatorsInUse = Collections.synchronizedList(new ArrayList<ElevatorState>());
    public List<Occupant> occupantsWaiting = Collections.synchronizedList(new ArrayList<Occupant>());

    /**
     * Initialize Rules Processor
     */
    @PostConstruct
    public void setup() throws Exception {
        try {
            synchronized (this.elevatorsInUse) {
                //Grab an ElevatorState from the pool...
                this.acquireReusable();
                this.acquireReusable();
                this.acquireReusable();
                this.acquireReusable();
            }
        } catch (Exception ex) {
            log.error("BaseMDB.setup(), Exception:", ex);
        }
    }

    public ElevatorResponse processRequest(ElevatorRequest request) {
        ElevatorResponse response = new ElevatorResponse();
        try {
            if (request.body != null) {
                for (Occupant occupant : request.body.occupantsEntering) {
                    occupantsWaiting.add(occupant);
                }
                for (Occupant occupant : occupantsWaiting) {
                    if(elevatorsInUse.size() == 0) {


                    } else {
                        for (int count = 0; count < elevatorsInUse.size(); count++) {
                            //Check to see if there is an elevator stopped and waiting at the current floor the occupants are
                            if ((elevatorsInUse.get(count).currentFloor == request.body.currentFloor) && (elevatorsInUse.get(count).currentDirection == request.body.direction)) {
                               // for (Occupant occupant : request.body.occupantsEntering) {
                               //     elevatorsInUse.get(count).addOccupant(occupant);
                               // }
                            }
                        }
                    }
                }
            } else {
               // log.error("ReceiverBase.receiveMessage(), messagePayLoad is null for queue {}", queueName);
            }
        } catch (Exception rtex) {
            throw new RuntimeException("Corridnator(), Error when getting ElevatorState from pool");
        } finally {

        }
        return response;
    }

    public ElevatorState acquireReusable() throws Exception {
        try {
            if (elevatorsInUse.size() != 0) {
                for (int count = 0; count < elevatorsInUse.size(); count++) {
                    if (!elevatorsInUse.get(count).inUse) {
                        elevatorsInUse.get(count).inUse = true;
                        return elevatorsInUse.get(count);
                    }
                }

                if (elevatorsInUse.size() < maxSizePoolSize) {
                    ElevatorState item = (ElevatorState) pooledTargetSource.getTarget();
                    item.inUse = true;
                    elevatorsInUse.add(item);
                    return item;
                } else {
                    log.warn("Corridnator.releaseReusable(), Max Pool size reached {} ", maxSizePoolSize);
                    throw new Exception("ElevatorState max pool size reached");
                }

            } else if (elevatorsInUse.size() < maxSizePoolSize) {
                ElevatorState item = (ElevatorState) pooledTargetSource.getTarget();
                item.inUse = true;
                elevatorsInUse.add(item);
                return item;
            }
        } catch (Exception ex) {
            log.error("Corridnator.acquireReusable(), Exception:", ex);
        }
        return null;
    }
}
