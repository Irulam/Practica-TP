package simulator.view;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChangeBodiesTableModel extends AbstractTableModel{
	private static final String columnNames[] = {"Body", "Type", "Position", "Velocity"};
	private int numberOfBodies;
	private ArrayList<String> _bodies;
	private JSONArray _types;
	private JSONArray _position;
	private JSONArray _velocity;
	
	ChangeBodiesTableModel(){
		_bodies = new ArrayList<String>();
		for(int i=1; i<= numberOfBodies; i++) {
			_bodies.add("b"+i);
		}
	}
	@Override
	public int getRowCount() {
		return columnNames.length;
	}

	@Override
	public int getColumnCount() {
		return numberOfBodies;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
			case 0: return _bodies.get(rowIndex);
			case 1: return _types.getJSONObject(rowIndex);
			case 2: return _position.getJSONObject(rowIndex);
			case 3: return _velocity.getJSONObject(rowIndex);
			default: return null;
		}
	}
	
	@Override
	public void setValueAt(Object val, int rowIndex, int columnIndex) {
		switch(columnIndex) {
			case 1: _types.put(rowIndex, val);
			case 2: _position.put(rowIndex,val);
			case 3: _velocity.put(rowIndex,val);
		}
	}

	public void setRows(int selection) {
		numberOfBodies = selection;
		
	}

}
