package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;
import java.lang.Math;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{

	public NewtonUniversalGravitationBuilder() {
		super("nlug", "newton universal laws");
	}

	@Override
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		JSONObject data = new JSONObject();
		info.put("type","nlug");
		//data.put("G", 6.67*Math.pow(10, -11));
		data.put("G", "the gravitational constant (a number)");
		info.put("data",data);
		info.put("desc", "Newton's law of universal gravitation");
		return info;
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject jo) {		
		return new NewtonUniversalGravitation();
	}

}
