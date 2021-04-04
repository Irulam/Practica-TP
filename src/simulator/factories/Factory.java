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
	/*permite obtener valores válidos para una instancia, he elegido poner los valores por defecto
	 * los elementos de la lista son estructuras json con valores por defecto (otra opción era usar 
	 * strings que describiesen los objetos a fabricar
	 * Cada elemento de la lista contiene una clave desc que contiene 
	 * una string que describe la plantilla 
	 */
	public List<JSONObject> getInfo();
}
