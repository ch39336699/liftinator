package com.capacity.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * Class to encode/decode JSON messages to/from given Java class
 *
 * NOTE: I tried Jersey initially, got a nullpointerexception. When I tried
 *       Jackson, I got a USEFUL error message (bad JSON construction). I thought about
 *       re-trying Jersey, but decided to stick with Jackson, for the intelligible
 *       error message(s) if nothing else. Dan.
 *       
 *       In case debate returns, here's the Jersey syntax: 
 *           JSONJAXBContext jctx = new JSONJAXBContext( Msg.class);
 *           JSONUnmarshaller jsonCoder = jctx.createJSONUnmarshaller();
 *           result = jsonCoder.unmarshalFromJSON( new StringReader( tmp ),  Msg.class);
 */
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
