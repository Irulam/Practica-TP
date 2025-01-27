package simulator.factories;

import org.json.JSONObject;

import simulator.model.Body;
import simulator.model.MassLossingBody;


public class MassLossingBodyBuilder extends BasicBodyBuilder{

	public MassLossingBodyBuilder() {
		super("mlb", "mass lossing body");
	}

	@Override
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		JSONObject data = new JSONObject();
		info.put("type","mlb");
		info.put("id","b1");
		data.put("p", new double [] {-3.5, 0.0});
		data.put("v", new double [] {0.0, 1.4});
		data.put("m", 3.0);
		data.put("freq",1.0);
		data.put("factor", 1.0);
		info.put("data", data);
		info.put("desc", "Mass lossing body");
		return info;
	}

	@Override
	protected Body createTheInstance(JSONObject info) {
		double lossFactor = info.getDouble("factor");
		double lossFrequency = info.getDouble("freq");
		
		return new MassLossingBody(super.createTheInstance(info), lossFactor, lossFrequency);	
	}

}
