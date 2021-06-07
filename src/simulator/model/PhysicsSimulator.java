package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator{
	private double tReal; 
	private double tActual;
	private ForceLaws laws; 
	private List<Body> bodies;
	private List<SimulatorObserver> observers;

	
	public PhysicsSimulator(double tReal, ForceLaws laws) {
		if(tReal<0 || laws == null) {
			throw new IllegalArgumentException("Argumentos del simulador no válidos");
		}
		this.tReal = tReal;
		this.laws = laws;
		this.bodies = new ArrayList<>();
		this.tActual = 0;
		this.observers = new ArrayList<>();
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
		
		for(SimulatorObserver observer: observers) {
			observer.onAdvance(bodies, tActual);
		}
	}
	
	public void resetForce() {
		for (Body body : bodies) {
			body.resetForce();
		}
	}
	

	public void addBody(Body b) {
		if (bodies.contains(b)) throw new IllegalArgumentException();
		else bodies.add(b);
		
		for(SimulatorObserver observer: observers) {
			observer.onBodyAdded(bodies, null);
		}
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
	
//---------	Métodos de la práctica 2-----------------------------------------------------------------------------------------------------------------
		
	public void reset() {
		bodies = new ArrayList<>();
		tActual = 0;
		for(SimulatorObserver observer: observers) {
			observer.onReset(bodies, tActual, tReal, laws.toString());
		}
	}
	
	//actualiza el delta time
	public void setDeltaTime(double tReal) {
		if(tReal<0) {
			throw new IllegalArgumentException("tReal no válido");
		}
		this.tReal = tReal;
		
		for(SimulatorObserver observer: observers) {
			observer.onDeltaTimeChanged(tReal);
		}
	}
	
	public void setForceLawsLaws(ForceLaws forceLaws) {
		if(forceLaws == null) {
			throw new IllegalArgumentException("No se está aplicando ninguna fuerza");
		}
		laws = forceLaws;
		
		for(SimulatorObserver observer: observers) {
			observer.onForceLawsChanged(laws.toString());
		}
	}
	
	public void setBodyInfo(int num, double mass) {
		bodies.get(num).setMass(mass);
	}
	
	/*TODO: al ser de una interfaz es posible que tenga un booleano
	para saber que está en la lista*/
	public void addObserver(SimulatorObserver o) {
		observers.add(o);
		o.onRegister(bodies, tActual, tReal, laws.toString());
	}
}
