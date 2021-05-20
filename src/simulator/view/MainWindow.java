package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame{
	private Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
	super("Physics Simulator");
	_ctrl = ctrl;
	initGUI();
	}
	private void initGUI() {
		//Paneles necesarios
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel centerPanel = new JPanel();
		//Layout 
		centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS)); 
		
		ControlPanel control_panel = new ControlPanel(_ctrl); 
		setContentPane(mainPanel);
		control_panel.setVisible(true);
		mainPanel.add(control_panel, BorderLayout.PAGE_START);
		
		StatusBar status_bar = new StatusBar(_ctrl);
		status_bar.setVisible(true);
		mainPanel.add(status_bar, BorderLayout.PAGE_END);
		
		JComponent bodies_table = new BodiesTable(_ctrl);
		bodies_table.setVisible(true);
		centerPanel.add(bodies_table);
		
		Viewer viewer = new Viewer(_ctrl);
		viewer.setVisible(true);
		centerPanel.add(viewer);
		mainPanel.add(centerPanel);
	}
	
	/*Para controlar el tamaño inicial de cada componente puedes usar el método setPreferredSize.
También necesitarás hacer visible la ventana, etc.
*/
		
}
