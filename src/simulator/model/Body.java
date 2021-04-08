package simulator.model;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Body {
	private String id;
	private Vector2D velocity;
	private Vector2D force;
	private Vector2D position;
	private double mass;
	
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
	
	/*TODO: la velocidad si deberia cambiar porque solo depende del tiempo
	 * y la posicion porque aunque la aceleracion sea 0 la velocidad no
	 * el argumento de tiempo real que es el que se está recibiendo aqui 
	 * parece que se esta imprimiendo todo el rato el primer paso de la simulación 
	 * en el que estos valores no han cambiado
	 */
	
	void move(double t) {
		Vector2D aceleration = new Vector2D();
		
		if (mass != 0.0) {
			aceleration = force.scale(1.0/mass);	
		}
		
		Vector2D velPrev = velocity;

		velocity = velocity.plus(aceleration.scale(t));
		position = position.plus(velPrev.scale(t)).plus(aceleration.scale(0.5*Math.pow(t, 2)));
		
		/*
		Vector2D velocityAux = velocity.scale(t);
		Vector2D acelerationAux = aceleration.scale((1.0/2.0)*Math.pow(t,2.0));
		Vector2D acelerationAux2 = aceleration.scale(t);
	    
		position = position.plus(velocityAux);
		position = position.plus(acelerationAux);
		velocity = velocity.plus(acelerationAux2);
		*/	
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
	
}
