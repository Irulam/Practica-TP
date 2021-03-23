package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body>{
	
	public BasicBodyBuilder(String type, String description) {
		super(type,description);
	}

	@Override
	protected Body createTheInstance(JSONObject info) {
		String id = info.getString("id");
		double m = info.getDouble("m");
		Vector2D p = createVector2D(info, "p");
		Vector2D v = createVector2D(info, "v");
		Vector2D f = createVector2D(info, "f");
		
		return new Body(id, v, f, p, m);

	}
	
	@Override
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		info.put("id", "id");
		info.put("m", "Mass of the object");
		info.put("p", "Position");
		info.put("v", "Velocity");
		info.put("f", "Force");
		return info;
	}
	
	protected Vector2D createVector2D(JSONObject info, String string){
		JSONArray infoA = info.getJSONArray(string);
		return new Vector2D(infoA.getDouble(0),infoA.getDouble(1));	
	}

}
