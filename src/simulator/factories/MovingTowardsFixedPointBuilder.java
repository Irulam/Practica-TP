package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws>{

	public MovingTowardsFixedPointBuilder(String type, String description) {
		super(type, description);
	}
	
	public MovingTowardsFixedPointBuilder() {
		super("mtcp", "moving towards fixed point");
	}

	@Override
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		JSONObject data = new JSONObject();
		info.put("type","mtcp");
		data.put("c",new double [] {0.0});
		data.put("g",9.81);
		info.put("data",data);
		info.put("desc", "moving towards fixed point");
		return info;
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject jo) {
		
		return new MovingTowardsFixedPoint();
	}

}
