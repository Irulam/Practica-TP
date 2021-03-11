package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class PhysicsSimulator {
	private double tReal; //TODO: exception
	private ForceLaws laws; //TODO: exception
	private List<Body> bodies;

	
	public PhysicsSimulator(double tReal, ForceLaws laws) {
		this.tReal = tReal;
		this.laws = laws;
		this.bodies = new ArrayList<>();
	}
	
	public void advance() {
		
	}
	
	public void addBody(Body b) {
		//lanzar excepcion
	}
	
	//public JSONObject getState() {}
	
	//public String toString()

}
