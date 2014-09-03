package org.cosgix.ttmobileapp.webservices;
import android.util.Log;
/**
 * @author admin
 * Runnable Implementation to execute the request through multi-threading.
 * 
 */
public class RequestExecuter implements Runnable{

private final String TAG = "RequestExecuter";
	
	private RequestProcessor processor;
	
	/**
	 * Creates the Runnable type.
	 * @param processor COSRestRequestProcessor type object to process
	 *  Request.
	 */
	public RequestExecuter(RequestProcessor processor)
	{
		this.processor = processor;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// called this method to process the request.
		processor.processRequest();
		Log.d(TAG, "Execution Finished.");
	}
}
