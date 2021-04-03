package simulator.factories;

import org.json.JSONObject;

//Crea una instancia de un tipo T
public abstract class Builder<T> {
	protected String _type;
	protected String _description;
	
	public Builder (String type, String description) {
		_type = type;
		_description = description;
	}
	
	
	/*si no es capaz de crear la instancia del tipo indicado devuelve null,
	 * en caso de ser correcto el tipo pero no los datos lanza una excepción
	 */
	/*lanza una excepcion IllegalArgument, para ello dentro
	 * de el constructor de una clase tendrá que lanzar la excepción 
	 * correspondiente
	 */
	public T createInstance(JSONObject info) {
		T b = null;
		if (_type != null && _type.equals(info.getString("type")))
			b = createTheInstance(info.getJSONObject("data"));
		return b;
	}
	
	/*Devuelve un objeto JSON que sirve de plantilla para 
	 * crear una instancia
	 */
	public abstract JSONObject getBuilderInfo();	
	protected abstract T createTheInstance(JSONObject jo);

}
