package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
	private JLabel _currTime;
	private JLabel _numOfBodies;
	private JLabel _currLaws;
	private JPanel _panel;
	
	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
		_panel = new JPanel();
	}
	private void initGUI() {
		this.setLayout( new FlowLayout( FlowLayout.LEFT ));
		this.setBorder( BorderFactory.createBevelBorder( 1 ));
		add(_currTime = new JLabel());
		add(new JSeparator());
		add(_numOfBodies = new JLabel());
		add(new JSeparator());
		add(_currLaws = new JLabel());
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double tReal, String fLawsDesc) {
		currTime(time);
		numBodies(bodies);
		currLaws(fLawsDesc);
	}

	@Override
	public void onReset(List<Body> bodies, double time, double tReal, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		numBodies(bodies);	
	}
	
	private void numBodies(List<Body> bodies) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_numOfBodies.setText("Bodies: " + bodies.size());
			}
		});	
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		currTime(time);	
	}
	
	private void currTime(double time) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_currTime.setText("Time: " + time);
				
			}
		});	
	}
	@Override
	public void onDeltaTimeChanged(double tReal) {

		
	}
	

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		currLaws(fLawsDesc);
	}
	
	private void currLaws(String fLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_currLaws.setText("Laws: " + fLawsDesc);
				
			}
		});	
	}
	public Object getProgressBar() {
		// TODO Auto-generated method stub
		return null;
	}

}
