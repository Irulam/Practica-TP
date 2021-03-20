package simulator.control;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Body;

public class MassEqualState implements StateComparator{

	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		Iterator<String> keys1 = s1.keys();
		Iterator<String> keys2 = s2.keys();
		JSONArray jar1 = s1.getJSONArray("bodies");
		JSONArray jar2 = s2.getJSONArray("bodies");
		
		return keys1.next().equals(keys2.next()) && jar1.get(0).equals(jar2.get(0)) 
												&& jar1.get(1).equals(jar2.get(1));	
	}

}
