package simulator.view;

import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChangeBodiesTableModel extends AbstractTableModel{
	private static final String columnNames[] = {"Body", "Type", "Position", "Velocity","Mass"};
	private int numberOfBodies;
	private ArrayList<String> _bodies;
	private JSONArray _types;
	private JSONArray _position;
	private JSONArray _velocity;
	private JSONArray _mass;
	
	ChangeBodiesTableModel(){
		numberOfBodies = 0;
		_bodies = new ArrayList<String>();
		_types = new JSONArray();
		_position = new JSONArray();
		_velocity = new JSONArray();
		_mass = new JSONArray();
	}
	
	@Override
	public String getColumnName(int row) {
		return columnNames[row];
	}
	
	@Override
	public int getRowCount() {
		return numberOfBodies;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
			case 0: return _bodies.get(rowIndex);
			case 1: return _types.opt(rowIndex);
			case 2: return _position.opt(rowIndex);
			case 3: return _velocity.opt(rowIndex);
			default: return null;
		}
	}
	
	@Override
	public void setValueAt(Object val, int rowIndex, int columnIndex) {
		switch(columnIndex) {
			case 1:  _types.put(rowIndex,val);
			case 2: _position.put(rowIndex,val);
			case 3: _velocity.put(rowIndex,val);
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1 || columnIndex == 2 || columnIndex == 3;
	}
	
	public void setRows(int selection) {
		numberOfBodies = selection;
		for(int i=1; i<= numberOfBodies; i++) {
			_bodies.add("b"+i);
		}
		fireTableDataChanged();
		
	}
	
	public JSONObject getJSON() {
		JSONObject info = new JSONObject();
		JSONArray jsonBodies = new JSONArray();
		for(int i=0;i<numberOfBodies;i++) {
		}
		info.put("bodies", jsonBodies);
		return info;
		
	}
	@Override
	public void fireTableDataChanged() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ChangeBodiesTableModel.super.fireTableDataChanged();
			}
		});
	}
}
