package simulator.factories;

import org.json.JSONObject;

import simulator.control.StateComparator;

public class MassEqualStateBuilder extends Builder<StateComparator>{

	public MassEqualStateBuilder() {
		super("mass equal", "mass equal state builder");
		// TODO Auto-generated constructor stub
	}

	@Override
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		JSONObject data = new JSONObject();
		info.put("type", "masseq");
		info.put("data", data);
		return info;
	}

	@Override
	protected StateComparator createTheInstance(JSONObject jo) {
		// TODO Auto-generated method stub
		return null;
	}

}
