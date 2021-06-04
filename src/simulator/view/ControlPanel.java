package simulator.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;


import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {

	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private Boolean _stopped;
	private static final String ICONS_DIR = "resources/icons/";
	private static final int SEPARATOR_PADDING = 0;
	private static final Number DEFAULT_STEPS = 10000;
	private JButton _exitButton;
	private JButton _openButton;
	private JButton _runButton;
	private JButton _stopButton;
	private JButton _changeLawsButton;
	private JSpinner _stepsField;
	private JTextField _deltaField;
	private int _delta;
	private JFileChooser fileChooser;
	private String fLawsDesc;

	public ControlPanel(Controller _ctrl) {
		this._ctrl = _ctrl;
		_stopped = true;
		initGUI();
		_ctrl.addObserver(this);
		this.fileChooser = new JFileChooser();

		fileChooser.setCurrentDirectory(new File("resources/examples"));
	}

	private void initGUI() {

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		add(_openButton = createButton("open.png", "Open file"));
		_openButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadFileIcon();
			}
		});

		add(createSeparator());

		add(_changeLawsButton = createButton("physics.png", "Change force law"));
		_changeLawsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLaws();

			}
		});

		add(createSeparator());

		add(_runButton = createButton("run.png", "Run simulation"));
		_runButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				run();
			}

		});

		add(_stopButton = createButton("stop.png", "Stop simulation"));
		_stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});

		add(createStepsInput());

		add(Box.createHorizontalGlue());// para que se quede a la derecha
		add(createSeparator());
		add(_exitButton = createButton("exit.png", "Exits the program"));
		_exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showOptionDialog(null, "Are you sure you want to exit the program?", "Exit Window",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Yes", "No" },
						"No");
				if (n == JOptionPane.YES_OPTION)
					System.exit(0);

			}
		});
		enableToolBar(true);
	}

	private JButton createButton(String imageFileName, String toolTipText) {
		JButton ret = new JButton();
		ret.setIcon(new ImageIcon(ICONS_DIR + imageFileName));
		ret.setToolTipText(toolTipText);
		return ret;
	}
	
	private void changeFieldSize(JComponent _stepsField) {
		int h = _stepsField.getFontMetrics(_stepsField.getFont()).getHeight() + 10;
		int w = _stepsField.getFontMetrics(_stepsField.getFont()).getMaxAdvance() * 4;

		_stepsField.setPreferredSize(new Dimension(w, h));
		_stepsField.setMaximumSize(new Dimension(w * 2, h));
		_stepsField.setMinimumSize(new Dimension(w, h));
	}
	private JPanel createStepsInput() {
		JPanel ret = new JPanel();
		ret.setToolTipText("Number of steps to run");
		ret.setLayout(new BoxLayout(ret, BoxLayout.X_AXIS));

		JLabel label = new JLabel(" Ticks: ");
		JLabel label_delta = new JLabel(" Delta-Time: ");
		_stepsField = new JSpinner(new SpinnerNumberModel(DEFAULT_STEPS, 1, null, 100));
		_deltaField = new JTextField("2500.0");

		changeFieldSize(_stepsField);
		changeFieldSize(_deltaField);
		
		ret.add(label);
		ret.add(_stepsField);
		add(createSeparator());
		ret.add(label_delta);
		ret.add(_deltaField);
		
		return ret;
	}

	private JComponent createSeparator() {
		JSeparator ret = new JSeparator(JSeparator.VERTICAL);
		ret.setMaximumSize(
				new Dimension(ret.getPreferredSize().width + SEPARATOR_PADDING, ret.getMaximumSize().height));

		return ret;
	}

	protected void changeLaws() {
		LawsDialog ld = new LawsDialog(_ctrl);
		// Este codigo no se ejecuta hasta que se cierra
		// _ctrl.setForceLaws(ld.getSelected());
		
		/*
		ArrayList<String> options = new ArrayList<>();
		for (JSONObject jo : _ctrl.getForceLawsInfo()) {
			options.add(jo.getString("desc"));
		}
		
		String response = (String) JOptionPane.showInputDialog(this, "Select gravity laws to be used.",
				"Gravity Laws Selector", JOptionPane.PLAIN_MESSAGE, null, options.toArray(), null);

		// Tres opciones para pasar como options al inputDialog:
		// 1. Usar ForceLaws -> Tienes que crear un metodo setForceLaws(ForceLaws) en lugar de JSON
		// 2. Usar JSONObject -> ??? -> Pasar JSONObject
		// 3. Usar Strings? -> Hace falta convertir luego de String a JSONObject igualmente
		
		for (JSONObject jo: _ctrl.getForceLawsInfo()) {
			if (jo.getString("desc").equals(response)) {
				_ctrl.setForceLaws(jo);
			}
		}
		*/
	}

	// Crea un diálogo para abrir ficheros
	public void loadFileIcon() {
		JDialog dialogFiles = new JDialog();
		dialogFiles.setTitle("Carga del fichero de eventos");
		int returnVal = fileChooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			_ctrl.reset();
			File file = fileChooser.getSelectedFile();

			try {
				InputStream in = new FileInputStream(file.getPath());
				_ctrl.loadBodies(in);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this, "Could not load file: " + e1.getMessage(), "Loading Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			System.out.println("load cancelled by user.");
		}
	}

	public void run() {
		try {
			int steps = (Integer) _stepsField.getValue();
			if (steps < 0)
				throw new IllegalArgumentException("Steps and delay should be greater than 0");
			enableToolBar(false);
			_stopped = false;
			_ctrl.setDeltaTime(Double.parseDouble(_deltaField.getText()));
			run_sim(steps);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Couldn't start simulation", "Simulation Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Could not start simulation: " + ex.getMessage(), "Simulation error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void enableToolBar(boolean enabled) {
		_openButton.setEnabled(enabled);
		_runButton.setEnabled(enabled);
		_stopButton.setEnabled(!enabled);
		_stepsField.setEnabled(enabled);
		_changeLawsButton.setEnabled(enabled);

	}

	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					run_sim(n - 1);
				}
			});
		} else {
			_stopped = true;
			enableToolBar(true);
		}
	}

	private void stop() {
		_stopped = true;
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double tReal, String fLawsDesc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReset(List<Body> bodies, double time, double tReal, String fLawsDesc) {
		// TODO Auto-generated method stub_dialog

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