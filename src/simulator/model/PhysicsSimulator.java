package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	private double tReal; //TODO: exception
	private double tActual;
	private ForceLaws laws; //TODO: exception
	private List<Body> bodies;

	
	public PhysicsSimulator(double tReal, ForceLaws laws) {
		this.tReal = tReal;
		this.laws = laws;
		this.bodies = new ArrayList<>();
	}
	
	public void advance() {
		
		resetForce();
		laws.apply(bodies);
		
		for (Body body : bodies) {
			body.move(tReal);
		}
		
		tActual += tReal;

	}
	
	public void resetForce() {
		for (Body body : bodies) {
			body.resetForce();
		}
	}
	

	public void addBody(Body b) {
		if (bodies.contains(b)) throw new IllegalArgumentException();
		else bodies.add(b);
	}
	
	public JSONObject getState() {
		JSONObject json = new JSONObject();
		json.put("time", tActual);
		JSONArray jar = new JSONArray();
		for (Body body : bodies) {
			jar.put(body.getState());
		}
		json.put("bodies", jar);
		return json;
	}
	
	public String toString() {
		return getState().toString();
	}
}
