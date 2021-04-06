package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;

public class Controller {
	private PhysicsSimulator _simulator; //Permite ejecutar las operaciones 
	private Factory<Body> _factory; //Construir cuerpos
	private Factory<ForceLaws> _laws;
	
	//Recibe al simulador y una factoría de cuerpos
	public Controller(PhysicsSimulator simulator, Factory<Body> factory) {
		_simulator = simulator;
		_factory = factory;
	}
	
	/*Carga los cuerpos dados en formato JSON
	 * Primero convierte los json a jsonobject para después crear cuerpos
	 * con esta información en las factorías y añadirlos al simulador*/
	public void loadBodies(InputStream in) {
		JSONObject jIn = new JSONObject(new JSONTokener(in));
		JSONArray jarray = jIn.getJSONArray("bodies");
		for (int i = 0; i < jarray.length(); ++i) {
			_simulator.addBody(_factory.createInstance(jarray.getJSONObject(i)));
		}
	}
	
	// Avanza cierto numero de pasos
	public void run(int n) {
		for (int i = 0; i <= n; ++i)
			_simulator.advance();
	}
	
	/* Avanza cierto numero de pasos y muestra el estado del simulador obtenido mediante el método toString
	 * en cada uno de dichos pasos, compara el estado del simulador con el esperado y lanza un error si no coinciden */
	public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) throws RunControllerException {
		if (n <= 0) throw new IllegalArgumentException("Pasos incorrectos");
	
		JSONArray jexpOutStates = null;
		JSONArray jOutStates = new JSONArray();
		
		PrintStream printStream = new PrintStream(out);
		
		if (expOut != null) jexpOutStates = (new JSONObject(expOut)).getJSONArray("states"); 
		
		printStream.print("{ \"states\" : [ ");
		
		for (int i = 0; i <= n; ++i) {	
			JSONObject s = _simulator.getState();
			
			if (i != 0) {
				printStream.print(", ");
			}
			
			printStream.print(s + "\n");
			if (expOut != null && !cmp.equal(s, jexpOutStates.getJSONObject(i))) {
				throw new RunControllerException("El estado del paso " + i + "no es el esperado");
			}
			
			jOutStates.put(s);
			_simulator.advance();
		}

		JSONObject jOut = new JSONObject();
		printStream.print(" ] }");
		jOut.put("states", jOutStates);
		
		printStream.print(jOut.toString());
		printStream.close();
	}
	

	public void resetForce() {
		_simulator.resetForce();
	}

	public Factory<ForceLaws> getGravityLawsFactory() {
		return this._laws;
	}
	
	public void setForceLaws(JSONObject info) {
		this._simulator.setForceLaws(_laws.createInstance(info));
	}

}
