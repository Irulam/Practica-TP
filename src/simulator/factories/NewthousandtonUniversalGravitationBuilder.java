package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewthousandtonUniversalGravitation;

public class NewthousandtonUniversalGravitationBuilder extends Builder<ForceLaws>{

	public NewthousandtonUniversalGravitationBuilder() {
		super("nlugt", "almost newton universal laws");
	}

	@Override
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		JSONObject data = new JSONObject();
		info.put("type","nlug");
		data.put("G", "the gravitational constant (a number)");
		data.put("aplicaConstante", "applies or not G");
		info.put("data",data);
		info.put("desc", "Newton's law of universal gravitation modified");
		return info;
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject jo) {
		double g = jo.optDouble("G", 6.67e-11);
		boolean aplicaConstante = jo.optBoolean("aplicaConstante", true);
		return new NewthousandtonUniversalGravitation(g,aplicaConstante);
	}

}
