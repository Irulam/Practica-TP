package simulator.factories;

import org.json.JSONObject;

public abstract class Builder<T> {
	protected String _type;
	protected String _description;
	
	public Builder (String type, String description) {
		_type = type;
		_description = description;
	}
	
	public T createInstance(JSONObject info) {
		T b = null;
		if (_type != null && _type.equals(info.getString("type")))
			b = createTheInstance(info.getJSONObject("data"));
		return b;
	}
	
	public abstract JSONObject getBuilderInfo();	
	protected abstract T createTheInstance(JSONObject jo);

}
