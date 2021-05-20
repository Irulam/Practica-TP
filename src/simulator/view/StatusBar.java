package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
		add(_numOfBodies = new JLabel());
		add(_currLaws = new JLabel());
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
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_numOfBodies.setText("Bodies: " + bodies.size());
			}
		});		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeltaTimeChanged(double tReal) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_currTime.setText("Time: " + tReal);
				
			}
		});	
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
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
