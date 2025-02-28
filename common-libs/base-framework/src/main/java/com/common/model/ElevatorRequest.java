package com.common.model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class ElevatorRequest {
    public RequestHdr hdr;
    public Body body;

    public class Body {
        @ApiModelProperty(notes = "Current Floor")
        public int currentFloor;

        @ApiModelProperty(notes = "Number of occupants entering")
        public int numEntering;

        @ApiModelProperty(notes = "Number of occupants exiting")
        public int numExiting;

        @ApiModelProperty(notes = "Elevator Direction of occupants entering(up/down)")
        public int direction;

        @ApiModelProperty(notes = "Secure occupants count")
        public int secureCount;
    }

}
