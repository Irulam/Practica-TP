package simulator.factories;

import java.util.List;

import org.json.JSONObject;

//Factoria que usa genéricos
public interface Factory<T> {
	/*info se usa para saber qué instancia hay que crear
	 * info contiene a la clave type para saber el tipo de la instancia y
	 * la clave data con el resto de información 
	 */
	public T createInstance(JSONObject info);
	//permite obtener valores válidos para una instancia 
	public List<JSONObject> getInfo();
}
