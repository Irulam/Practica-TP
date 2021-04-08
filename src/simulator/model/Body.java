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
	
	/*Cuando se les solicita se pueden mover. Al moverse cambian sus parámtros de acuerdo a 
	las leyes físicas aplicadas*/
	public Body(String id, Vector2D velocity, Vector2D position, double mass){
		this.id = id;
		this.velocity = velocity;
		this.position = position;
		this.mass = mass;
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
		aceleration = new Vector2D(force.getX()/mass,force.getY()/mass);
		Vector2D velocityAux = velocity.scale(t);
		Vector2D acelerationAux = aceleration.scale((1/2)*Math.pow(t,2));
		Vector2D acelerationAux2 = aceleration.scale(t);
	    
		position.plus(velocityAux);
		position.plus(acelerationAux);
		velocity.plus(acelerationAux2);		
	}
	
	/*TODO: quizas hay que usar asJSONArray 
	 */
	public JSONObject getState() {
		JSONObject data = new JSONObject();
		data.put("id", id);
		data.put("m", mass);
		data.put("p", position);
		data.put("v", velocity);
		data.put("f", force);
		return data;

		// Vector2D f = createVector2D(info, "f");
	}

	public void setAcceleration(Vector2D newAceleration) {
		aceleration = newAceleration;
		
	}
	
	public String toString() {
		return getState().toString();
	}
	
}
