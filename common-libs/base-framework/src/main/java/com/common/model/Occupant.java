package com.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Occupant {
    @ApiModelProperty(notes = "Person's name")
    public String name;

    @ApiModelProperty(notes = "Current Floor")
    public int currentFloor;

    @ApiModelProperty(notes = "Elevator Direction of occupants entering(up/down)")
    public String direction;

    @ApiModelProperty(notes = "Person's weight")
    public int weight;

    @ApiModelProperty(notes = "Missed an elevator. So currently waiting.")
    public boolean missed;

    @ApiModelProperty(notes = "Floor Selected to travel to")
    public int floorSelected;

}
