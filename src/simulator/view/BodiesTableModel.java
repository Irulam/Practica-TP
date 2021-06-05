package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver{
	private List<Body> _bodies;
	private static final String columnNames[] = {"Id", "Mass", "Position", "Velocity", "Force"};
	
	BodiesTableModel(Controller ctrl){
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}
	@Override
	public int getRowCount() {
		return _bodies.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
			case 0: return _bodies.get(rowIndex).getId();
			case 1: return _bodies.get(rowIndex).getMass();
			case 2: return _bodies.get(rowIndex).getPosition();
			case 3: return _bodies.get(rowIndex).getVelocity();
			case 4: return _bodies.get(rowIndex).getForce();
			default: return null;
		}

	}
	
	@Override
	public void fireTableDataChanged() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				BodiesTableModel.super.fireTableDataChanged();
			}
		});
	}


	@Override
	public void onRegister(List<Body> bodies, double time, double tReal, String fLawsDesc) {
		_bodies = bodies;
		fireTableDataChanged();
	}

	@Override
	public void onReset(List<Body> bodies, double time, double tReal, String fLawsDesc) {
		_bodies = bodies;
		fireTableDataChanged();

	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		_bodies = bodies;
		fireTableDataChanged();

	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		_bodies = bodies;
		fireTableDataChanged();

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
