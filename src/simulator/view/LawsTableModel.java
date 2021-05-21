package simulator.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class LawsTableModel extends AbstractTableModel implements SimulatorObserver{
	private static final String columnNames[] = {"Key", "Value", "Description"};
	private JSONObject _info;
	private JSONArray _keys;
	private JSONArray _description;
	
	LawsTableModel(Controller ctrl){
		ctrl.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0: return _keys.get(rowIndex);
		case 1: return _info.get(rowIndex).getMass();
		case 2: return _description.get(rowIndex);
		default: return null;
		}
	}
	public void setInfo(JSONObject info) {
		this._info = info;
		_keys = info.names();
		_description = info.
	}
	
	public JSONObject getInfo() {
		return _info;
	}
	@Override
	public void onRegister(List<Body> bodies, double time, double tReal, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double tReal, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeltaTimeChanged(double tReal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

}
