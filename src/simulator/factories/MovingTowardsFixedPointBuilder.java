package simulator.factories;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws>{

	public MovingTowardsFixedPointBuilder(String type, String description) {
		super(type, description);
	}
	
	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "moving towards fixed point");
	}

	@Override
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		JSONObject data = new JSONObject();
		info.put("type","mtfp");
		data.put("c","the point towards which bodies move (a json list of 2 numbers");
		data.put("g","the length of the acceleration vector (a number)");
		info.put("data",data);
		info.put("desc", "Moving towards fixed point");
		return info;
	}
	
	protected double [] toDouble(JSONArray jarray) {
		double[] darray = new double[jarray.length()];
		for (int i = 0; i < jarray.length(); ++i)
			darray[i] = jarray.getDouble(i);
		
		return darray;
	}
	
	@Override
	protected ForceLaws createTheInstance(JSONObject jo) {
		double g = 0;
		JSONArray jc = jo.optJSONArray("c");
		double[] c = new double[] {0.0};
		try {
			if (jc != null) {
				c = toDouble(jc);
			}
			if (jo.isNull("g")) {
				g = 9.81;
			}else {
				g = jo.getDouble("g");
			}
		}catch(JSONException ex) {
			JOptionPane.showMessageDialog(null, "Something went wrong: " + ex.getMessage(), 
					"ERROR", JOptionPane.ERROR_MESSAGE);
		}
		return new MovingTowardsFixedPoint(g, c);
	}
	
}
