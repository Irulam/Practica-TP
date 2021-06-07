package simulator.model;
import org.json.JSONObject;

import simulator.misc.Vector2D;


public class Body {
	protected String id;
	protected Vector2D velocity;
	protected Vector2D force;
	protected Vector2D position;
	protected double mass;
	
	/*Cuando se les solicita se pueden mover. Al moverse cambian sus parámtros de acuerdo a 
	las leyes físicas aplicadas*/
	public Body(String id, Vector2D velocity, Vector2D position, double mass){
		this.id = id;
		this.velocity = velocity;
		this.position = position;
		this.mass = mass;
		this.force = new Vector2D();
	}
	
	public Body(Body createTheInstance) {
		this.id = createTheInstance.id;
		this.velocity = createTheInstance.velocity;
		this.position = createTheInstance.position;
		this.mass = createTheInstance.mass;
		this.force = createTheInstance.force;
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
		force = new Vector2D();
	}
	
	void move(double t) {
		Vector2D aceleration = new Vector2D();
		
		if (mass != 0.0) {
			aceleration = force.scale(1.0/mass);	
		}
		
		Vector2D velPrev = velocity;

		velocity = velocity.plus(aceleration.scale(t));
		position = position.plus(velPrev.scale(t)).plus(aceleration.scale(0.5*Math.pow(t, 2)));	
	}

	public JSONObject getState() {
		JSONObject data = new JSONObject();
		data.put("id", id);
		data.put("m", mass);
		data.put("p", position.asJSONArray());
		data.put("v", velocity.asJSONArray());
		data.put("f", force.asJSONArray());
		return data;

	}

	public String toString() {
		return getState().toString();
	}
	
	public void setForce(Vector2D fab) {
		force = new Vector2D(fab);
	}

	public void resetVelocity() {
		velocity = new Vector2D();
		
	}

	public void setMass(double mass) {
		this.mass = mass;		
	}
	
	public void setVelocity()
	
}
