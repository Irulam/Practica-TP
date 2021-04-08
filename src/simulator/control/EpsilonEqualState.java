package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class EpsilonEqualState implements StateComparator{
	double eps;
	public EpsilonEqualState(double eps) {
		this.eps = eps;
	}
	public boolean equal(JSONObject s1, JSONObject s2) {
		JSONArray bodiesS1 = s1.getJSONArray("bodies");
		JSONArray bodiesS2 = s2.getJSONArray("bodies");
		boolean equal = bodiesS1.length() == bodiesS2.length() && s1.get("time").equals(s1.get("time"));
		int i = 0;
		
		while(equal&&i<bodiesS1.length()) {
			JSONObject bodyS1 = bodiesS1.getJSONObject(i);
			JSONObject bodyS2 = bodiesS2.getJSONObject(i);
			
			equal = bodyS1.get("id").equals(bodyS2.get("id")) &&
					// equalModuleEps(bodyS1.getDouble("m"),bodyS2.getDouble("m")) &&
					// equalModuleEps(bodyS1.getJSONArray("p"), bodyS2.getJSONArray("p")) &&
					// equalModuleEps(bodyS1.getJSONArray("v"), bodyS2.getJSONArray("v")) &&
					equalModuleEps(bodyS1.getJSONArray("f"), bodyS2.getJSONArray("f"));
			i++;
		}
		
		return equal;
	}
	
	private boolean equalModuleEps(double a, double b) {
		return Math.abs(a-b)<= eps;
	}
	
	private boolean equalModuleEps(Vector2D v1, Vector2D v2){
		return v1.distanceTo(v2) <= eps;
	}
	
	private boolean equalModuleEps(JSONArray v1, JSONArray v2) {
		return equalModuleEps(new Vector2D(v1.getDouble(0), v1.getDouble(1)), new Vector2D(v2.getDouble(0), v2.getDouble(1)));
	}
}
