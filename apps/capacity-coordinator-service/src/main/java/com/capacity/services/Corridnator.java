package com.capacity.services;

import com.common.model.ElevatorRequest;
import com.common.model.ElevatorResponse;
import com.common.model.Occupant;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.aop.target.CommonsPool2TargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

import static net.logstash.logback.argument.StructuredArguments.kv;

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
     * Initialize Elevators
     */
    @PostConstruct
    public void setup() throws Exception {
        try {
            Hashtable<Integer, String> names = new Hashtable<Integer, String>();

            // Adding elements to the Hashtable
            names.put(0, "Elevator A");
            names.put(1, "Elevator B");
            names.put(2, "Elevator C");
            names.put(3, "Elevator D");
            names.put(4, "Elevator E");
            names.put(5, "Elevator F");
            names.put(5, "Elevator G");
            names.put(5, "Elevator H");
            for (int count = 0; count < 8; count++) {
                ElevatorState item = (ElevatorState) pooledTargetSource.getTarget();
                item.name = names.get(count);
                item.inUse = true;
                elevatorsInUse.add(item);
            }
        } catch (Exception ex) {
            log.error("BaseMDB.setup(), Exception:", ex);
        }
    }

    public ElevatorResponse processRequest(ElevatorRequest request) {
        ElevatorResponse response = new ElevatorResponse();
        int minDistance = 3;
        try {
            if (request.body != null) {
                for (Occupant occupant : request.body.occupantsEntering) {
                    if(occupant.currentFloor == occupant.floorSelected)
                    {
                        JSONObject data = new JSONObject();
                        data.put("msg", "Invalid Entry: Current Floor Selected");
                        data.put("name", occupant.name);
                        data.put("currentFloor", occupant.currentFloor);
                        data.put("floorSelected", occupant.floorSelected);
                        log.info("ElevatorState : {} ", kv("STATUS", data));
                    }
                    occupantsWaiting.add(occupant);
                }
                for (Iterator<ElevatorState> elevatorsIterator = elevatorsInUse.iterator(); elevatorsIterator.hasNext();) {
                    if(occupantsWaiting.size() == 0)
                    {
                        break;
                    }
                    ElevatorState elevator = elevatorsIterator.next();
                    elevator.stopped = true;
                    for (Iterator<Occupant> occupantsIterator = occupantsWaiting.iterator(); occupantsIterator.hasNext();)  {
                        Occupant occupant = occupantsIterator.next();
                        int distance = Math.abs(elevator.currentFloor - occupant.currentFloor);
                        if (occupant.currentFloor > occupant.floorSelected) {
                            occupant.direction = "DOWN";
                        } else {
                            occupant.direction = "UP";
                        }
                        if ((distance <= minDistance) && (elevator.currentDirection.equals(occupant.direction)) && (elevator.currentFloor == occupant.currentFloor)  &&  (elevator.occupantsCumulativeWeight + occupant.weight < 800)) {
                            elevator.addOccupant(occupant);
                            occupantsIterator.remove();
                        } else {
                            if(elevator.currentFloor == 0 && elevator.occupants.size() == 0)
                            {
                                occupantsIterator.remove();
                                elevator.occupantsPickUp.add(occupant);
                                JSONObject data = new JSONObject();
                                data.put("msg", "Elevator Dispatched");
                                data.put("elevator", elevator.name);
                                data.put("occupant_count", elevator.occupants.size());
                                data.put("currentFloor", elevator.currentFloor);
                                data.put("pickup_name", occupant.name);
                                data.put("pickup_floor", occupant.currentFloor);
                                log.info("ElevatorState : {} ", kv("STATUS", data));
                            }
                            elevator.stopped = false;
                            break;
                           // log.warn("Corridnator.releaseReusable(), Max Pool size reached {} ", maxSizePoolSize);
                        }
                    }
                    elevator.stopped = false;
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
