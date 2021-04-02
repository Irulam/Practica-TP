package simulator.factories;

import org.json.JSONObject;

import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator>{

	public EpsilonEqualStatesBuilder() {
		super("eps equal", "epsilon equal states builder");
	}

	@Override
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		JSONObject data = new JSONObject();
		info.put("type", "epseq");
		data.put("eps", 0.0);
		info.put("data", data);
		return info;
	}

	@Override
	protected StateComparator createTheInstance(JSONObject jo) {
		// TODO Auto-generated method stub
		return null;
	}

}
