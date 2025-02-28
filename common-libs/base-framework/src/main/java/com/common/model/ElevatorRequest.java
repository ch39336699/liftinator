package com.common.model;
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
    private RequestHdr hdr;
    private RequestBody body;

}
