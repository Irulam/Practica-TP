package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	private double tReal; 
	private double tActual;
	private ForceLaws laws; 
	private List<Body> bodies;

	
	public PhysicsSimulator(double tReal, ForceLaws laws) {
		if(tReal<0 || laws == null) {
			throw new IllegalArgumentException("Argumentos del simulador no válidos");
		}
		this.tReal = tReal;
		this.laws = laws;
		this.bodies = new ArrayList<>();
		this.tActual = 0;
	}
	

	/*Para cada paso de la simulación solicita a los cuerpos que se muevan
	 * según las leyes de fuerza impuestas
	 */
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
	
	/*Proporciona información sobre los cuerpos en el estado actual.
	 * Dicha información es solicita por el controlador a la hora de 
	 * mostrar el estado de la simulación.
	 */
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

	/*TODO: Es posible cambiar las leyes de fuerza gracias a este método, no sale en el enunciado pero he asumido 
	 * que está porque cada vez que avanza el simulador resetea las leyes de fuerza
	 
	public void setForceLaws(ForceLaws laws) {
		if(laws == null) {
			throw new IllegalArgumentException("Debe haber alguna ley de fuerza");
		}
		this.laws = laws;
	}
	*/
}
