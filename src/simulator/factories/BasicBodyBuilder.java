package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body>{
	
	public BasicBodyBuilder() {
		super("basic","basic body");
	}
	
	public BasicBodyBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}

	@Override
	protected Body createTheInstance(JSONObject info) {
		String id = info.getString("id");
		double m = info.getDouble("m");
		Vector2D p = createVector2D(info, "p");
		Vector2D v = createVector2D(info, "v");
		// Vector2D f = createVector2D(info, "f");
		
		return new Body(id, v, p, m);

	}
	
	@Override
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		JSONObject data = new JSONObject();
		info.put("type","basic");
		info.put("id","b1");
		data.put("p", new double [] {0.0, 0.0});
		data.put("v", new double [] {0.05, 0.0});
		data.put("m", 5.97);
		info.put("data", data);
		info.put("desc", "basic body");
		return info;
	}
	
	protected Vector2D createVector2D(JSONObject info, String string){
		JSONArray infoA = info.getJSONArray(string);
		return new Vector2D(infoA.getDouble(0),infoA.getDouble(1));	
	}

}
