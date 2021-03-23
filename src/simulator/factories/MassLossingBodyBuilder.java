package simulator.factories;

import org.json.JSONObject;

import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLossingBodyBuilder extends BasicBodyBuilder{

	public MassLossingBodyBuilder(String type, String description) {
		super(type, description);
	}

	@Override
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		info.put("factor", "loss factor");
		info.put("freq", "loss frequency");
		return info;
	}

	@Override
	protected Body createTheInstance(JSONObject info) {
		double lossFrequency = info.getDouble("freq");
		double lossFactor = info.getDouble("factor");
		
		return new MassLossingBody(super.createTheInstance(info), lossFrequency, lossFactor);	
	}

}
