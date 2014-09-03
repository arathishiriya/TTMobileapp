package org.cosgix.ttmobileapp.webservices;

public class RequestManager {
	private static final String TAG = "RequestManager";
	/**
	 * Process the Rest Request in the same thread.
	 * 
	 * @param processor RequestProcessor type object to process the 
	 * request.
	 * 
	 */
	public static final void processRequest(
			RequestProcessor processor) {
		processor.processRequest();
		
	}
	/**
	 * Process the Rest Request in the same thread or in new thread depends upon
	 * isSeparateExecutionNeeded boolean type value.
	 * 
	 * @param processor type object to process the 
	 * request.
	 * 
	 * @param isSeparateExecutionNeeded boolean type value to equates the execution behavior
	 * Either in same thread or different thread.
	 * 
	 */
	public static final void processRequest(
			RequestProcessor processor, boolean isSeparateExecutionNeeded) {
		if(isSeparateExecutionNeeded)
		{	
			RequestExecuter executerInterface = new RequestExecuter(processor);
			Thread executer = new Thread(executerInterface);
			executer.start();
		}
		else
		{
			RequestManager.processRequest(processor);
		}	
	}

}
