package simulator.control;


import org.json.JSONArray;
import org.json.JSONObject;

public class MassEqualState implements StateComparator{

	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		JSONArray bodiesS1 = s1.getJSONArray("bodies");
		JSONArray bodiesS2 = s2.getJSONArray("bodies");
		boolean equal = bodiesS1.length() == bodiesS2.length() && s1.get("time").equals(s1.get("time"));
		int i = 0;
		
		while(equal&&i<bodiesS1.length()) {
			JSONObject bodyS1 = bodiesS1.getJSONObject(i);
			JSONObject bodyS2 = bodiesS2.getJSONObject(i);
			equal = bodyS1.get("id").equals(bodyS2.get("id")) &&
					bodyS1.get("mass").equals(bodyS2.get("mass"));
			i++;
		}
		
		return equal;
	}

}
