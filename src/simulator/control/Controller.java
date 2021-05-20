package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {
	private PhysicsSimulator _simulator; //Permite ejecutar las operaciones 
	private Factory<Body> _factory; //Construir cuerpos
	private Factory<ForceLaws> _laws;
	
	//Recibe al simulador y una factoría de cuerpos
	public Controller(PhysicsSimulator simulator, Factory<Body> factory, Factory<ForceLaws> laws) {
		_simulator = simulator;
		_factory = factory;
		_laws = laws;
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
	
	/* Avanza cierto numero de pasos y muestra el estado del simulador obtenido mediante el método toString
	 * en cada uno de dichos pasos, compara el estado del simulador con el esperado y lanza un error si no coinciden */
	public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) throws RunControllerException {
		if (n <= 0) throw new IllegalArgumentException("Pasos incorrectos");
	
		JSONArray jexpOutStates = null;
		JSONArray jOutStates = new JSONArray();
		
		PrintStream printStream = new PrintStream(out);
		
		printStream.println("{ \"states\":[ ");
		
		if (expOut != null) {
			JSONObject auxJo = (new JSONObject(new JSONTokener(expOut)));
			jexpOutStates = auxJo.getJSONArray("states"); 
		}
		
		for (int i = 0; i <= n; ++i) {	
			JSONObject s = _simulator.getState();
			
			jOutStates.put(s);
			printStream.print(s.toString(2));
			printStream.println((i!=n)?",":"");
			
			if (expOut != null && !cmp.equal(s, jexpOutStates.getJSONObject(i))) {		
				throw new RunControllerException("El estado del paso " + i + " no es el esperado, se esperaba:\n" +
					jexpOutStates.getJSONObject(i).toString(2)
				);
			}
			
			_simulator.advance();
		}
		
		printStream.print("]}");
		printStream.close();
	}
	

	public void resetForce() {
		_simulator.resetForce();
	}

	
//---------	Métodos de la práctica 2-----------------------------------------------------------------------------------------------------------------
/*Métodos que forman parte de la implementación del modelo vista controlador, o bien permiten 
  controlar la ejecución o bien permiten cambiar parámetros de la simulación que antes no se podían 
  cambiar en ejecución*/
	
	public void reset() {
		_simulator.reset();
	}
	
	//He cambiado el nombre del parámetro respecto al enunciado para que sea coherente
	public void setDeltaTime(double tReal) {
		_simulator.setDeltaTime(tReal);
	}
	
	public void addObserver(SimulatorObserver o) {
		_simulator.addObserver(o);
	}
	
	// Avanza cierto numero de pasos
	public void run(int n) {
		for (int i = 0; i <= n; ++i)
			_simulator.advance();
	}
	
	public List<JSONObject> getForceLawsInfo(){
		return _laws.getInfo();
	}
	
	public void setForceLaws(JSONObject info) {
		_simulator.setForceLawsLaws(_laws.createInstance(info));
	}

}
