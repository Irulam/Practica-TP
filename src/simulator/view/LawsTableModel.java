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
	
	LawsTableModel() {
		
	}
	
	LawsTableModel(JSONObject info) {
		_info = info;
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
			// case 1: return _data.get(dataKey);
			case 2: return _info.getJSONObject("data").getString(dataKey);
			default: return null;
		}
	}
	
	@Override
	public void setValueAt(Object val, int rowIndex, int columnIndex) {
		String dataKey = _info.getJSONObject("data").names().getString(rowIndex);
		// TODO: Donde esta el val
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1;
	}
	
	public void setInfo(JSONObject info) {
		this._info = info;
		fireTableDataChanged();
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
