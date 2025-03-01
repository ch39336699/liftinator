package com.capacity.services;

import com.common.model.ElevatorRequest;
import com.common.model.ElevatorResponse;
import com.common.model.Occupant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Slf4j
@Service("com.capacity.services.ElevatorState")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ElevatorState {
    public boolean inUse;
    public boolean stopped;
    public int currentFloor;
    public int furthestFloor;
    public int currentDirection;
    public int maxWnt;
    public int topFloor;
    public ArrayList<Occupant> occupants = new ArrayList<Occupant>(); // Create an ArrayList object
    private static final long INTERVAL = 1000 * 60 * 2; // 5 min

    public void addOccupant(Occupant occupant) {
        try {
            occupants.add(occupant);
        } catch (Exception ex) {
            throw ex;
        } finally {

        }
    }

    public void removeOccupant(Occupant occupant) {
        try {
            occupants.remove(occupant);
        } catch (Exception ex) {
            throw ex;
        } finally {

        }
    }

//    @Scheduled(fixedDelay = 5000) // Execute every 5 seconds to simulate moving between floors
//    public void myTask() {
//        if(currentDirection == 1)
//        {
//            //Going up
//            if(currentFloor < topFloor)
//            {
//                currentFloor++;
//            }
//        } else {
//            //Going down
//            if(currentFloor > 0 )
//            {
//                currentFloor--;
//            }
//        }
//        for (Occupant occupant : occupants) {
//            if(occupant.floorSelected == currentFloor)
//            {
//                occupants.remove(occupant);
//            }
//        }
//    }
}
