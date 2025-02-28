package com.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestHdr {
    private Credentials cred;

    @ApiModelProperty(notes = "LDAP Emp number for user making request")
    private String empID;

    public String toString() {
        if (cred != null) {
            return "{ empID:" + empID + ", Credentials:" + cred.toString() + "}";
        } else {
            return "{ empID:" + empID + "}";
        }
    }
}
