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

@SuppressWarnings("SpringJavaAutowiringInspection")
@Slf4j
@Service("com.capacity.services.ElevatorState")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ElevatorState {
    public boolean inUse;
    public int currentFloor = 0;
    public int furthestFloor;
    public int currentDirection = 1;
    public int maxWeight = 800;
    public int occupantsCumulativeWeight;
    public int topFloor = 10;
    public ArrayList<Occupant> occupants = new ArrayList<Occupant>(); // Create an ArrayList object

    public void addOccupant(Occupant occupant) {
        JSONObject data = new JSONObject();
        data.put("currentFloor", currentFloor);
        data.put("occupant_count", occupants.size());
        data.put("occupants_cumulative_weight", occupantsCumulativeWeight);
        try {
            if(occupantsCumulativeWeight + occupant.weight < maxWeight) {
                occupants.add(occupant);
                data.put("msg", "Occupant Embarked");
                data.put("name", occupant.name);
                data.put("weight", occupant.weight);
                occupantsCumulativeWeight = occupantsCumulativeWeight + occupant.weight;
                for (Occupant occupant2 : occupants) {
                    furthestFloor = Math.max(furthestFloor, occupant2.floorSelected);
                }
            } else {
                data.put("msg", "Maximum weight reached");
                data.put("name", occupant.name);
                data.put("weight", occupant.weight);
            }
            log.info("ElevatorState : {} ", kv("STATUS", data));
        } catch (Exception ex) {
            log.error("ElevatorState.addOccupant(): Exception: {}", ExceptionUtils.getStackTrace(ex));
        } finally {

        }
    }

    public void update() {
        try {
            JSONObject data = new JSONObject();
            if((occupants.size() == 0) && currentFloor == 0) {
                //no occupants and at ground floor so nothing to do.
                currentDirection = 1;
                return;
            }
            if (currentDirection == 1) {
                //Going up
                if (currentFloor < topFloor) {
                    currentFloor++;
                }
            } else {
                //Going down
                if (currentFloor > 0) {
                    currentFloor--;
                }
            }
            for (Iterator<Occupant> it = occupants.iterator(); it.hasNext(); ) {
                Occupant occupant = it.next();
                if (occupant.floorSelected == currentFloor) {
                    if (furthestFloor == occupant.floorSelected) {
                        currentDirection = 0;
                    }
                    occupantsCumulativeWeight = occupantsCumulativeWeight - occupant.weight;
                    data.put("msg", "Occupant Disembarked");
                    data.put("name", occupant.name);
                    data.put("weight", occupant.weight);
                    it.remove();
                }
            }
            data.put("occupant_count", occupants.size());
            data.put("currentFloor", currentFloor);
            data.put("occupants_cumulative_weight", occupantsCumulativeWeight);
            if (currentDirection == 0) {
                data.put("direction", "Down");
            } else {
                data.put("direction", "Up");
            }
            log.info("ElevatorState : {} ", kv("STATUS", data));
        } catch (Exception ex) {
            log.error("ElevatorState.update(): Exception: {}", ExceptionUtils.getStackTrace(ex));
        } finally {

        }
    }

}
