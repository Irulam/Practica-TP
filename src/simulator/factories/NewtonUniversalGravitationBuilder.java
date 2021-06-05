package simulator.factories;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{

	public NewtonUniversalGravitationBuilder() {
		super("nlug", "newton universal laws");
	}

	@Override
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		JSONObject data = new JSONObject();
		info.put("type","nlug");
		data.put("G", "the gravitational constant (a number)");
		info.put("data",data);
		info.put("desc", "Newton's law of universal gravitation");
		return info;
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject jo) {
		double g = 0;
		try {
			if (jo.isNull("G")) {
				g = 6.67e-11;
			}else {
				g = jo.getDouble("G");
			}
		}catch(JSONException ex) {
			JOptionPane.showMessageDialog(null, "Something went wrong: " + ex.getMessage(), 
					"ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		return new NewtonUniversalGravitation(g);
		
	}

}
