package simulator.factories;

import org.json.JSONObject;

import simulator.control.MassEqualState;
import simulator.control.StateComparator;

public class MassEqualStateBuilder extends Builder<StateComparator>{

	public MassEqualStateBuilder() {
		super("mass equal", "mass equal state builder");
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
		
		return new MassEqualState();
	}

}
