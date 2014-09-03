package org.cosgix.ttmobileapp.webservices;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import android.util.Log;

public class GETRequestProcessor extends RequestProcessor{

	private static final String TAG = "GETRequestProcessor";
	
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
	 * @param map Rest Request Parameters needed to send on to the server.
	 * 
	 * @param parser Rest Parser to parse the server response.
	 * 
	 */
	public GETRequestProcessor(String url, String path,
			WebServicesConst requestType,
			IResponseHandler responseHandler, HashMap<String, String> map, IResponseParser parser) {
		super(url, path, requestType, responseHandler, map,  parser);
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
	 * @param map Rest Request Parameters needed to send on to the server.
	 * 
	 */
	public GETRequestProcessor(String url, String path,
			WebServicesConst requestType,
			IResponseHandler responseHandler, HashMap<String, String> map) {
		super(url, path, requestType, responseHandler, map,  null);
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
	public GETRequestProcessor(String url, String path,
			WebServicesConst requestType,
			IResponseHandler responseHandler, int connectionTimeOut,
			int responseTimeOut, HashMap<String, String> map, IResponseParser parser) {
		super(url, path, requestType, responseHandler, connectionTimeOut,
				responseTimeOut, map, parser);
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
	 */
	public GETRequestProcessor(String url, String path,
			WebServicesConst requestType,
			IResponseHandler responseHandler, int connectionTimeOut,
			int responseTimeOut, HashMap<String, String> map) {
		super(url, path, requestType, responseHandler, connectionTimeOut,
				responseTimeOut, map, null);
	}
	/* (non-Javadoc)
	 * 
	 */
	protected String generateURL() {
		String modifiedUrl = null;
		StringBuilder queryString = new StringBuilder();
		//For queries particular to a id, we need to append ?
		//Not for the general query
		if(map != null)
		{
			Set<String> keys = (Set<String>) map.keySet();
			Iterator<String> iterator = keys.iterator();
			queryString.append("?");
			while(iterator.hasNext())
			{
				String key = iterator.next();
				String value = map.get(key);
				queryString.append(key + "=" + value);
				queryString.append("&");
			}
		}	
		
		
		if(path == null || path.trim().equals(""))
		{
			modifiedUrl = url + queryString;
		}
		else
		{
			modifiedUrl = url + "/" + path + queryString;
		}	
		
		return modifiedUrl;
	}
	/* (non-Javadoc)
	 * @see 
	 */
	@Override
	protected HttpURLConnection setConnectionsProperty(HttpURLConnection connection) {
		try 
		{
			// Setting the request type post.
			connection.setRequestMethod("GET");

			// Setting the request connect time out.
			connection.setConnectTimeout(connectionTimeOut);

			// Setting the response time out.
			connection.setReadTimeout(responseTimeOut);

			connection.setUseCaches(false);
			
			
		}
		catch (ProtocolException e) 
		{
			// ProtocolException :  Protocol Error.
			
			
			Log.e(TAG, "ProtocolException while setting Connection Property : " + e);
		}
		catch(Exception e)
		{
			// Exception :  Any Communication Problem during server and application.
			
			
			Log.e(TAG, "Exception while creating Connection Property : " + e);
		}
		
		return connection;
	}
	}

