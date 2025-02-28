package com.capacity.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Credentials {
    @ApiModelProperty(notes = "Group Credentials Base64 encrypted key")
    public String key;

    @ApiModelProperty(notes = "Group Credentials name")
    public String usr;

    public String toString() {
        return "{ key:" + key + ", usr:" + usr + "}";
    }
}
