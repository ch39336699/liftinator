package com.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;


@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class ElevatorRequest {
    public RequestHdr hdr;
    public Body body;

    public class Body {

        @ApiModelProperty(notes = "Number of occupants entering the elevator")
        public ArrayList<Occupant> occupantsEntering = new ArrayList<Occupant>();

    }

}
