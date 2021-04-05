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
		info.put("data", data);
		return info;
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject jo) {
		return new NoForce();
	}

}
