package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;


public class BuilderBasedFactory<T> implements Factory<T> {
	private List<Builder<T>> _constructores;
	private List<JSONObject> _objetos;
	
	public BuilderBasedFactory(List<Builder<T>> list) {
		_constructores = new ArrayList<>(list);
		_objetos = new ArrayList<>();
		
		for (Builder<T> builder : _constructores) {
			_objetos.add(builder.getBuilderInfo());
		}
	}
	
	/*Busca al constructor que pueda crear a un objeto correspondiente
	 * lanza una excepcion IllegalArgumentExcepcion 
	 */
	//TODO: no estoy segura de que esto lance una excepcion
	@Override
	public T createInstance(JSONObject info) {
		T instance = null;
		for (int i = 0; instance == null && i < _constructores.size(); ++i) {
			instance = _constructores.get(i).createInstance(info);
		}
		return instance;
	}

	@Override
	public List<JSONObject> getInfo() {
		return new ArrayList<>(_objetos);
	}


}
