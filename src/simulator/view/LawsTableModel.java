package simulator.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class LawsTableModel extends AbstractTableModel {
	private static final String columnNames[] = {"Key", "Value", "Description"};
	private JSONObject _info;
	private JSONObject _val;
	
	LawsTableModel() {
		
	}
	
	LawsTableModel(JSONObject info) {
		_info = info;
		_val = new JSONObject();
	}
	
	@Override
	public int getRowCount() {
		return _info.getJSONObject("data").length();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String dataKey = _info.getJSONObject("data").names().getString(rowIndex);
		
		switch(columnIndex) {
			case 0: return dataKey;
			case 1: return _val.opt(dataKey);
			case 2: return _info.getJSONObject("data").getString(dataKey);
			default: return null;
		}
	}
	
	@Override
	public void setValueAt(Object val, int rowIndex, int columnIndex) {
		if (columnIndex == 1) {
			String dataKey = _info.getJSONObject("data").names().getString(rowIndex);
			_val.put(dataKey, val);
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1;
	}
	
	public void setInfo(JSONObject info) {
		_info = info;
		_val = new JSONObject();
		fireTableDataChanged();
	}
	
	public JSONObject getInfo() {
		JSONObject ret =  new JSONObject(_info, JSONObject.getNames(_info));
		ret.put("data", _val);
		return ret;
	}
	
	@Override
	public void fireTableDataChanged() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LawsTableModel.super.fireTableDataChanged();
			}
		});
	}
}
