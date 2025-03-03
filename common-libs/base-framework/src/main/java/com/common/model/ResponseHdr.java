package com.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.datatype.XMLGregorianCalendar;

@Getter
@Setter
@NoArgsConstructor

public class ResponseHdr {
	@ApiModelProperty(notes="version")
	private String version;
	
	@ApiModelProperty(notes="API service title")
	private String title;
	
	@ApiModelProperty(notes="API service description")
	private String description;
	
	@ApiModelProperty(notes="Datetime request was complete")
	protected XMLGregorianCalendar tmstp;
}
