package org.cosgix.ttmobileapp.database;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This gives the application more control on various control
 * @author sanjib
 *
 */
public interface CoreDomain {
	
    public String toJson() throws JSONException;

    public void fromJson(JSONObject json);

}
