package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws>{

	public NoForceBuilder() {
		super("ng", "doesn't apply force");
	}

	@Override
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		JSONObject data = new JSONObject();
		info.put("type", "ng");
		//TODO: no es lo que pone en el enunciado, preguntar
		info.put("data", "{}");
		info.put("desc", "No force");
		return info;
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject jo) {
		return new NoForce();
	}

}
