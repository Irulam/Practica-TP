package simulator.factories;

import org.json.JSONObject;

import simulator.model.Body;
import simulator.model.MassOscilatingBody;

public class MassOscilatingBodyBuilder extends BasicBodyBuilder{
	public MassOscilatingBodyBuilder() {
		super("mob", "mass oscilating body");
	}

	@Override
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		JSONObject data = new JSONObject();
		info.put("type","mob");
		info.put("id","b1");
		data.put("p", new double [] {-3.5, 0.0});
		data.put("v", new double [] {0.0, 1.4});
		data.put("m", 3.0);
		data.put("factor", 1.0);
		info.put("data", data);
		info.put("desc", "Mass oscilating body");
		return info;
	}

	@Override
	protected Body createTheInstance(JSONObject info) {
		double oscilatingFactor = info.getDouble("factor");
		
		return new MassOscilatingBody(super.createTheInstance(info), oscilatingFactor);	
	}
}
