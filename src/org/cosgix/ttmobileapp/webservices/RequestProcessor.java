package org.cosgix.ttmobileapp.webservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import android.util.Log;

/**
* @author admin
* 
*  Request Processor 
* 
*/

abstract public  class RequestProcessor implements IResponseParser {

	private static final String TAG = "RequestProcessor";

	private static final int CONNECTION_TIME_OUT = 60 * 1000;
	private static final int RESPONSE_TIME_OUT = 5 * 60 * 1000;
	
	protected String url;
	protected String path;
	protected boolean isCancelled;
	
	protected HttpURLConnection connection;
	protected WebServicesConst requestType;
	protected IResponseHandler responseHandler;
	protected int connectionTimeOut;
	protected int responseTimeOut;
	protected HashMap<String, String> map;
	protected IResponseParser parser;
	protected Exception exception;
	
	/**
	 * Create the Processor to object of a Rest Type Request.
	 * 
	 * @param url uniform resource locator info.
	 * 
	 * @param path info needed to be append at the end of url.
	 * 
	 * @param requestType (HTTP Request Type) 
	 * 
	 * @param responseHandler Interface to notify the application about Rest Request Response.
	 * 
	 * @param map  Request Parameters needed to send on to the server.
	 * 
	 * @param parser  Parser to parse the server response.
	 * 
	 */
	public RequestProcessor(String url, String path, WebServicesConst requestType,
			IResponseHandler responseHandler, HashMap<String, String> map, IResponseParser parser) 
	{
		this.url = url;
		this.path = path;
		this.responseHandler = responseHandler;
		this.requestType = requestType;
		this.map = map;
		this.connectionTimeOut = CONNECTION_TIME_OUT;
		this.responseTimeOut = RESPONSE_TIME_OUT;
		this.parser = parser;
	}
	/**
	 * Create the Processor to object of a Rest Type Request.
	 * 
	 * @param url uniform resource locator info.
	 * 
	 * @param path info needed to be append at the end of url.
	 * 
	 * @param requestType (HTTP Request Type) 
	 * 
	 * @param responseHandler Interface to notify the application about Rest Request Response.
	 * 
	 * @param connectionTimeOut Represents time in mili-seconds to connect to the server.
	 * 
	 * @param responseTimeOut Represents the time to get the response from the server.
	 * 
	 * @param map Rest Request Parameters needed to send on to the server.
	 * 
	 * @param parser Rest Parser to parse the server response.
	 * 
	 */
	public RequestProcessor(String url, String path, WebServicesConst requestType,
			IResponseHandler responseHandler, int connectionTimeOut,
			int responseTimeOut, HashMap<String, String> map, IResponseParser parser) 
	{
		this.url = url;
		this.path = path;
		this.requestType = requestType;
		this.responseHandler = responseHandler;
		this.map = map;
		this.connectionTimeOut = connectionTimeOut;
		this.responseTimeOut = responseTimeOut;
		
		this.parser = parser;
	}
	/**
	 *  processRequest() a method to process the any kind of request. 
	 *  Contains the core logic to execute both Get or Post type Request.
	 */
	public void processRequest() 
	{
		
			// Getting the connection object of the HttpUrlConection Type.
			connection = this.getConnection();
				
			// If connection property setting is done successfully.
			// Set the Request Type (GET, POST) property for the connection.
			connection = this.setConnectionsProperty(connection);
			
			// Processing the Request.
			executeRequest(connection);
		
}
	/**
	 * Process the Rest Request.
	 * first get HTTP status code.
	 * Second gets string content from the server if needed by application.
	 * third parse the content if needed by application.
	 * 
	 * @param connection HttpUrlConnection type connection to process the Rest Request.
	 * 
	 */
	protected void executeRequest(HttpURLConnection connection) 
	{
		Log.d(TAG, "URL : " + connection.getURL());
		// If status Has been fetched.
		boolean isStatusFetch = false;
		// Input Stream Declaration.
		InputStream inputStream = null;
		
		try 
		{
		
			// Get HTTP Response Code.
			int statusCode = connection.getResponseCode();
			// Get Server Message.
			String message = connection.getResponseMessage();
				
			
			// Notify the HTTP Server status and Message.
			
			// Sets the flag true. 
			// If content Response is also needed by the application.
			boolean flag = responseHandler.onResponseRecieve(statusCode,message);
			
			// Sets isStatusFetch to true.
			isStatusFetch = true;
	
			// Getting Input Stream from the server.
			inputStream = connection.getInputStream();
					
					
			// Getting the content of the server response.
			String content = getResponseContent(inputStream);
			
			// If parsing response is also needed by the application.
			flag = responseHandler.onResponseContentReceive(content,statusCode);
			// Parsing the content.
			Object object = parseContent(content);
			responseHandler.onParsedDataReceive(object);
						
		} 
		catch (UnknownHostException e)
		{
			// Occurs when unable to reach to destination URL.
		Log.e(TAG, "UnknownHostException while processing Request : " + e);
		}
		catch (SocketTimeoutException e)
		{
			// Occurs when time out expires while during connection making.
			// Updating the progress status.
			
			Log.e(TAG, "SocketTimeoutException while processing Request : " + e);
		} 
		catch (IOException e)
		{
			// Occurs when application tried to read server input stream.
			
			// Updating the progress status.
			Log.e(TAG, "IOException while processing Request : " + e);
		}
		catch (Exception e) 
		{
			// Occurs when application communicate to the server.
			// Any kind of exception.
			
			// Updating the progress status.
			Log.e(TAG, "Exception while processing Request : " + e);

		}
		
		if (inputStream != null)
		{
			try 
			{
				// Closing Input Stream.
				inputStream.close();
				inputStream = null;
			} 
			catch (IOException e)
			{
				Log.e(TAG, "IOException while closing Input Stream : " + e);
			}
		}
}


/**
 * Read Server Input Stream and gets the content.
 * 
 * @param input Input Stream Object Received from the server.
 * 
 * @return Returns the string content in the response.
 */
public String getResponseContent(InputStream input) 
{
	StringBuilder content = new StringBuilder();
	InputStreamReader reader = null;
	BufferedReader bufferReader = null;
	try 
	{
		// Creating Reader Object form the input stream.
		reader = new InputStreamReader(input);
		
		// Decorating Reader with Buffer Mechanism.
		bufferReader = new BufferedReader(reader);
		
		String line = bufferReader.readLine();
		do
		{
			// Read Stream Content Line by Line.
			content.append(line);
			line = bufferReader.readLine();
		}
		while (line != null); // Check for termination.
		
	} 
	catch (SocketTimeoutException e)
	{
		// SocketTimeoutException : Occurs when time out expires while during connection making.
		
		Log.e(TAG, "SocketTimeoutException while processing Request : " + e);
	} 
	catch (IOException e) 
	{
		// IOException: While reading server response.
		
		Log.e(TAG, "IOException while writing response : " + e);
	}
	catch (Exception e)
	{
		// Exception: While reading server response.
		
		Log.e(TAG, "Exception while writing response : " + e);
	} 
	finally 
	{
		// Releasing the Resources.
		
		if (bufferReader != null) 
		{
			// closing the buffer reader.
			try 
			{
				bufferReader.close();
				bufferReader = null;
			} 
			catch (IOException e) 
			{
				Log.e(TAG, "Exception while closing reader stream : " + e);
			}
		}

		if (reader != null)
		{
			// closing the reader.
			try 
			{
				reader.close();
				reader = null;
			}
			catch (IOException e)
			{
				Log.e(TAG, "Exception while closing reader stream : " + e);
			}
		}
	}

	return content.toString();
}


@Override
public Object parseContent(String content) throws Exception
{
	Object obj = null;
	if (parser != null)
	{
		obj = parser.parseContent(content);
	}
	return obj;
}
/**
 * Creates the HttpUrlConnection for all type of request.
 * 
 * @return HttpURLConnection object.
 */
protected HttpURLConnection getConnection()
{
	HttpURLConnection connection = null;
	URL targetURL = null;

	try 
	{
		// Get the Modified URL and Creates the Target URL.
		targetURL = new URL(generateURL());
		
		// Creates the Connection Object.
		connection = (HttpURLConnection) targetURL.openConnection();
		
		
	}
	catch (MalformedURLException e) 
	{
		// MalformedURLException: Occurs if the url syntax is bad.
		
		// Updating the Progress Status.
		
		Log.e(TAG, "MalformedURLException while creating Connection : " + e);
	} 
	catch (IOException e) 
	{
		// IOException: Occurs while Reading server stream.
		
		
		Log.e(TAG, "IOException while creating Connection : " + e);
	}
	catch (Exception e)
	{
		// Exception: Occurs while communication to the server.
		
		Log.e(TAG, "Exception while creating Connection : " + e);
	}

	return connection;
}
/**
 * Modified the String url by appending the path and others info 
 * depends upon request type.
 * 
 * @return the String type value. 
 *  
 */
abstract protected String generateURL();

/**
 * Set the Connection Property depends upin the request type.
 * 
 * @param connection HttpURLConnection object.
 * 
 * @return return the modified connection object 
 */
abstract protected HttpURLConnection setConnectionsProperty(
		HttpURLConnection connection);
}


