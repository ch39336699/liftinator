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


/*
 * Project: liftinator
 * File: Corridnator.java
 * Author: Chris Harper
 * The Corridnator class is responsible for managing a set of elevators and their interactions with occupants
 * who request to use the elevators. It handles the assignment of elevators to occupants based on their current floor
 * and their destination, ensuring that each elevator operates within its capacity limits.
 * The class uses a pool of elevator objects and provides methods for handling requests and dispatching elevators.
 */
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
                    if (occupant.currentFloor == occupant.floorSelected) {
                        JSONObject data = new JSONObject();
                        data.put("msg", "Invalid Entry: Current Floor Selected");
                        data.put("name", occupant.name);
                        data.put("currentFloor", occupant.currentFloor);
                        data.put("floorSelected", occupant.floorSelected);
                        log.info("ErrorMsg : {} ", kv("STATUS", data));
                    }
                    occupantsWaiting.add(occupant);
                }
                for (Iterator<ElevatorState> elevatorsIterator = elevatorsInUse.iterator(); elevatorsIterator.hasNext(); ) {
                    if (occupantsWaiting.size() == 0) {
                        break;
                    }
                    ElevatorState elevator = elevatorsIterator.next();
                    elevator.stopped = true;
                    for (Iterator<Occupant> occupantsIterator = occupantsWaiting.iterator(); occupantsIterator.hasNext(); ) {
                        Occupant occupant = occupantsIterator.next();
                        int distance = Math.abs(elevator.currentFloor - occupant.currentFloor);
                        if (occupant.currentFloor > occupant.floorSelected) {
                            occupant.direction = "DOWN";
                        } else {
                            occupant.direction = "UP";
                        }
                        if ((distance <= minDistance) && (elevator.currentDirection.equals(occupant.direction)) && (elevator.currentFloor == occupant.currentFloor) && (elevator.occupantsCumulativeWeight + occupant.weight < 800)) {
                            elevator.addOccupant(occupant);
                            occupantsIterator.remove();
                        } else {
                            if (elevator.currentFloor == 0 && elevator.occupants.size() == 0) {
                                occupantsIterator.remove();
                                elevator.occupantsPickUp.add(occupant);
                                JSONObject data = new JSONObject();
                                String msg = "Dispatched to pickup " + occupant.name + " on floor " + occupant.currentFloor;
                                data.put("msg", msg);
                                data.put("elevator", elevator.name);
                                data.put("occupant_count", elevator.occupants.size());
                                data.put("currentFloor", elevator.currentFloor);
                                log.info("ElevatorMsg : {} ", kv("STATUS", data));
                            }
                            elevator.stopped = false;
                            break;
                        }
                    }
                    elevator.stopped = false;
                }

            }
        } catch (Exception rtex) {
            throw new RuntimeException("Corridnator(), Error when getting ElevatorState from pool");
        }
        return response;
    }

}
