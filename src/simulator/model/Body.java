package simulator.model;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Body {
	private String id;
	private Vector2D velocity;
	private Vector2D force;
	private Vector2D position;
	private double mass;
	private Vector2D aceleration;
	
	Body(String id, Vector2D velocity, Vector2D force, Vector2D position, double mass){
		this.id = id;
		this.velocity = velocity;
		this.force = force;
		this.position = position;
		this.mass = mass;
	}
	
	public String getId() {
		return this.id;
	}
	
	public Vector2D getVelocity() {
		return this.velocity;
	}
	
	public Vector2D getForce() {
		return this.force;
	}
	
	public Vector2D getPosition() {
		return this.position;
	} 
	
	public double getMass() {
		return this.mass;
	}
	
	void addForce(Vector2D f) {
		force.plus(f);
	}
	
	void resetForce() {
		force.plus(force);
	}
	
	void move(double t) {
		aceleration = new Vector2D(force.getX()/mass,force.getY()/mass);
		Vector2D velocityAux = velocity.scale(t);
		Vector2D acelerationAux =aceleration.scale((1/2)*Math.pow(t,2));
		Vector2D acelerationAux2 =aceleration.scale(t);
	    
		position.plus(velocityAux);
		position.plus(acelerationAux);
		velocity.plus(acelerationAux2);		
	}
	
	public JSONObject getState() {
		JSONObject data = new JSONObject();
		data.put("id", id);
		data.put("m", mass);
		data.put("p", position.toString());
		data.put("v", velocity.toString());
		data.put("f", force.toString());
		return data;

	}

	public void setAcceleration(Vector2D newAceleration) {
		aceleration = newAceleration;
		
	}
	
	//TODO
	/*
	public String toString()
	*/
}
