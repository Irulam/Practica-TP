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
	
	public Controller(PhysicsSimulator simulator, Factory<Body> factory, Factory<ForceLaws> laws) {
		_simulator = simulator;
		_factory = factory;
		_laws = laws;
	}
	
	/*Carga los cuerpos dados en formato JSON*/
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
	
	// Avanza cierto numero de pasos y muestra el estado del simulador para cada uno de ellos
	public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) {
		if (n <= 0) throw new IllegalArgumentException("Pasos incorrectos");
		PrintStream printStream = new PrintStream(out);
		
		printStream.print("{ \"states\" : [ ");
		for (int i = 0; i <= n; ++i) {
			if (i != 0) {
				printStream.print(", ");
			}
			JSONObject jexpOut = new JSONObject(expOut); 
			JSONObject jOut = new JSONObject(out);
			cmp.equal(jexpOut, jOut);
			printStream.print(_simulator.toString());
			_simulator.advance();
		}
		printStream.print(" ] }");
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
