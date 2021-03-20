package simulator.control;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class EpsilonEqualState implements StateComparator{
	double eps;
	public EpsilonEqualState(double eps) {
		this.eps = eps;
	}
	public boolean equal(JSONObject s1, JSONObject s2) {
		boolean equal;
		Iterator<String> keys1 = s1.keys();
		Iterator<String> keys2 = s2.keys();
		JSONArray jar1 = s1.getJSONArray("bodies");
		JSONArray jar2 = s2.getJSONArray("bodies");
		
		equal = equalModuleEps(jar1.getDouble(1),jar2.getDouble(1));
		
		/*
		for(int i=1; i<jar1.length();i++) {
			equal = equal && equalModuleEps(jar1.getClass(i),jar2.getDouble(i));
		}
		*/
		
		for(Object ob: jar1) {
			equalModuleEps(ob.getClass(),ob.getClass());
		}
		return keys1.next().equals(keys2.next()) && jar1.get(0).equals(jar2.get(0)) ;
	}
	
	public boolean equalModuleEps(double a, double b) {
		return Math.abs(a-b)<= eps;
	}
	
	public boolean equalModuleEps(Vector2D v1, Vector2D v2){
		return v1.distanceTo(v2)<=eps;
	}
}
