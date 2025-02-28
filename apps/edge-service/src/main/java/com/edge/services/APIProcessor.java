package com.edge.services;

import com.common.model.ElevatorRequest;
import com.common.model.ElevatorResponse;
import com.common.util.RestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class APIProcessor {

RestService restService = new RestService();

    public ElevatorResponse processRequest(ElevatorRequest request) {
        ElevatorResponse response = null;
        try {
             response =  restService.post("http://localhost:8082/elevatorAction", request);

//            if (messagePayLoad != null) {
//                try {
//                    //Grab a BaseMDB from the pool...
//                    synchronized (this._available) {
//                        baseMDB = this.acquireReusable();
//                    }
//                } catch (Exception ex) {
//                    log.error("ReceiverBase.receiveMessage(), Error when getting BaseMDB from pool for queue {}. Pool Active: {}, Pool Max: {}, Exception:", queueName, this._available.size(), this.rulesProcMaxSize, ex);
//                    throw new RuntimeException("ReceiverBase(), Error when getting BaseMDB from pool");
//                }
//                baseMDB.onMessage(messagePayLoad, headers, ruleset, recvID);
//            } else {
//                log.error("ReceiverBase.receiveMessage(), messagePayLoad is null for queue {}", queueName);
//            }
        } catch (RuntimeException rtex) {
            //Catch and throw back to JMS to put messages back on the queue.
           // log.warn("ReceiverBase.receiveMessage(), Rolling message back to JMS queue: {}. RuntimeException:", queueName, ExceptionUtils.getStackTrace(rtex));
            throw rtex;
        } finally {

        }
        return response;
    }


}
