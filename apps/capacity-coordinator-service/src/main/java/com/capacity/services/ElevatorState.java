package com.capacity.services;

import com.common.model.Occupant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

import static net.logstash.logback.argument.StructuredArguments.kv;

/*
 * Project: liftinator
 * File: ElevatorState.java
 * Author: Chris Harper
 * The ElevatorState class models an elevator's state, managing its current floor, direction, occupant information,
 * and the movement between floors. It provides methods to add new occupants and update the elevator's state
 *  (e.g., moving up/down, picking up and dropping off passengers). The class also logs updates related to the
 * elevator's actions, such as when an occupant is added or disembarks.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Slf4j
@Service("com.capacity.services.ElevatorState")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ElevatorState {
    public String name;
    public boolean stopped;
    public boolean inUse;
    public int currentFloor = 0;
    public int furthestFloor;
    public String currentDirection = "UP";
    public int occupantsCumulativeWeight;
    public int topFloor = 10;
    public ArrayList<Occupant> occupants = new ArrayList<Occupant>(); // Create an ArrayList object
    public ArrayList<Occupant> occupantsPickUp = new ArrayList<Occupant>();

    public void addOccupant(Occupant occupant) {
        try {
            occupants.add(occupant);
            JSONObject occupantData = new JSONObject();
            occupantsCumulativeWeight = occupantsCumulativeWeight + occupant.weight;
            for (Occupant occupant2 : occupants) {
                furthestFloor = Math.max(furthestFloor, occupant2.floorSelected);
            }
            if (currentDirection == null) {
                currentDirection = occupant.direction;
            }

            // Construct the message using StringBuilder for better performance (if needed in a loop)
            String msg = new StringBuilder("Added ")
                    .append(occupant.name)
                    .append(" at floor ")
                    .append(currentFloor)
                    .append(" going to floor ")
                    .append(occupant.floorSelected)
                    .toString();

            // Create JSONObject and populate it
            JSONObject elevatorData = new JSONObject();
            elevatorData.put("msg", msg);
            elevatorData.put("elevator", name);
            elevatorData.put("occupant_count", occupants.size());
            elevatorData.put("currentFloor", currentFloor);
            elevatorData.put("occupants_cumulative_weight", occupantsCumulativeWeight);

            // Log the message with structured data
            log.info("ElevatorMsg : {} ", kv("STATUS", elevatorData));
        } catch (Exception ex) {
            log.error("ElevatorState.addOccupant(): Exception: {}", ExceptionUtils.getStackTrace(ex));
        } finally {

        }
    }

    public void update() {
        StringBuffer disembarkedList = new StringBuffer();
        int disembarkedcnt = 0;
        try {
            JSONObject data = new JSONObject();
            if ((occupants.size() == 0) && (occupantsPickUp.size() == 0)) {
                if (currentFloor > 0) {
                    currentDirection = "DOWN";
                } else {
                    currentDirection = "UP";
                    return;
                }

            }

            if (occupantsPickUp.size() > 0) {
                currentDirection = occupantsPickUp.get(0).direction;
                if (occupantsPickUp.get(0).currentFloor == currentFloor) {
                    addOccupant(occupantsPickUp.get(0));
                    occupantsPickUp.remove(0);
                } else {
                    if (occupantsPickUp.get(0).currentFloor < currentFloor) {
                        currentDirection = "DOWN";
                    } else {
                        currentDirection = "UP";
                    }
                }
            }
            if (currentDirection.equals("UP") && currentFloor < topFloor) {
                currentFloor++;
            } else if (currentDirection.equals("DOWN") && currentFloor > 0) {
                currentFloor--;
            }

            for (Iterator<Occupant> it = occupants.iterator(); it.hasNext(); ) {
                Occupant occupant = it.next();
                if (occupant.floorSelected == currentFloor) {
                    if (furthestFloor == occupant.floorSelected) {
                        currentDirection = "DOWN";
                    }
                    disembarkedList.append(occupant.name).append(" ");
                    disembarkedcnt++;
                    occupantsCumulativeWeight -= occupant.weight;
                    it.remove();
                }
            }
            JSONObject elevatorData = new JSONObject();
            String msg = null;
            if (disembarkedcnt > 0) {
                msg = disembarkedcnt + " disembarked at floor " + currentFloor + " " + disembarkedList.toString();
            } else {
                msg = "No Stop at floor " + currentFloor + " going " + currentDirection;
            }
            elevatorData.put("msg", msg);
            elevatorData.put("elevator", name);
            elevatorData.put("occupant_count", occupants.size());
            elevatorData.put("currentFloor", currentFloor);
            elevatorData.put("occupants_cumulative_weight", occupantsCumulativeWeight);
            log.info("ElevatorMsg : {} ", kv("STATUS", elevatorData));
        } catch (Exception ex) {
            log.error("ElevatorState.update(): Exception: {}", ExceptionUtils.getStackTrace(ex));
        } finally {

        }
    }

}
