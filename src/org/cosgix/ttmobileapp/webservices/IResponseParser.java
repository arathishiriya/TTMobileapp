package org.cosgix.ttmobileapp.webservices;

/**
 * @author admin
 * Interface to parse the server content.
 * 
 */
public interface IResponseParser {

	/**
	 * Parse the response received from the server.
	 * 
	 * @param content String type content.
	 * 
	 * @return Object return type.
	 * 
	 * @throws Exception if something goes wrong during parsing
	 * 
	 */
	public Object parseContent(String content) throws Exception;
}
