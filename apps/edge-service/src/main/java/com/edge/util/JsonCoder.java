package com.edge.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonCoder {
 

	protected Class<?> packageClass; 
	protected ObjectMapper jacksonJsonMapper;

	/**
	 * ctor
	 * 
	 * @param _className
	 * @throws Exception
	 */
	public JsonCoder(Class<?> _className) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("init");
		}
		packageClass = _className;
		jacksonJsonMapper = new ObjectMapper();
	}

	/**
	 * Decode JSON message into Java Object
	 * 
	 * @param xmlByteArray
	 * @return
	 * @throws Exception
	 */
	public Object decode(byte[] xmlByteArray) throws Exception {
		if( log.isTraceEnabled() ) {
			log.trace("decode starting..");
		}
		return jacksonJsonMapper.readValue( xmlByteArray, packageClass );		
	}

	/**
	 * Encode JSON message from Java Object
	 * TODO: untested
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public byte[] encode(Object request) throws Exception {
		if( log.isTraceEnabled() ) {
			log.trace("encode starting...");
		}
//		String retVal = jacksonJsonMapper.writeValueAsString( request );
		byte[] retVal = jacksonJsonMapper.writeValueAsBytes( request );
		if( log.isDebugEnabled() ) {
			log.debug("Encoded: '"+new String( retVal )+"'");
		}
		return retVal;
	}
}
