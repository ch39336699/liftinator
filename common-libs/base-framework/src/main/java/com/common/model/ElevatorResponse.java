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
public class ElevatorResponse {
	private ResponseHdr hdr = new ResponseHdr();
	private ResponseBody body = new ResponseBody();

	@Getter
	@Setter
	public static class ResponseBody {
		@ApiModelProperty(notes="cmpl (complete), warn (warning), err (error)")
		protected String cd;
		
		@ApiModelProperty(notes="Details for errors or warnings.")
		protected String note;

	}
}
