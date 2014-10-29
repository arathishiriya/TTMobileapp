package org.cosgix.ttmobileapp.webservices;

/**
 * @author admin
 * 
 * Interface to parse the Server response into the 
 * application specific domain.
 * 
 */
public interface IResponseHandler {
	/**
	 * Notify the application about the HTTP Status code and Message.
	 * 
	 * @param statusCode HTTP Status Code.
	 * 
	 * @param message Server Message.
	 * 
	 * @return boolean type value, whether processing for the request done or not.
	 * Returned value false means need to be process more, true means finished.
	 * 
	 */
	public boolean onResponseRecieve(int statusCode, String message);
	/**
	 * Notify the application about the content received from the server.
	 * 
	 * @param content String type response retrieved from the server.
	 * 
	 * @return boolean type value, whether processing for the request done or not.
	 * Returned value false means need to be process more, true means finished.
	 * 
	 */
	public boolean onResponseContentReceive(String content,int statusCode);
	
	/**
	 * 
	 * Notify the application about the parsing event finished.
	 * 
	 * @param parsed content from the server into the application domain.
	 * 
	 * @return boolean type value, whether processing for the request done or not.
	 * Returned value false means need to be process more, true means finished.
	 * 
	 */
	public boolean onParsedDataReceive(Object obj);
	
	/**
	 * Notify about the progress status of Rest Request
	 * and exception during progress if occurs. 
	 * 
	 * @param status COSRestRequestProgressStatus type Rest Progress Status.
	 * 
	 * @param e Exception type object.
	 * 
	 */
	public void onRequestFailed( Exception e);
	
}
