package com.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ElevatorResponse {
	private ResponseHdr hdr = new ResponseHdr();
	private ResponseBody body = new ResponseBody();

	@Getter
	@Setter
	public static class ResponseBody {

	}
}
