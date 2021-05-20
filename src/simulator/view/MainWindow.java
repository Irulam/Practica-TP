package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import simulator.control.Controller;

public class MainWindow extends JFrame{
	private Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}
	
	private void initGUI() {
		BorderLayout borderLayout = new BorderLayout(5,5);
		this.setLayout(borderLayout);
		
		StatusBar sb = new StatusBar(_ctrl);
		this.add(sb, BorderLayout.PAGE_END);
		this.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
		
		JComponent tablaDeCuerpos = new BodiesTable(_ctrl);
		tablaDeCuerpos.setMinimumSize(new Dimension(0,0));
		Viewer viewer = new Viewer(_ctrl);
		JSplitPane centerPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablaDeCuerpos, viewer);
		centerPanel.setResizeWeight(0.2);
		// centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		this.add(centerPanel, BorderLayout.CENTER);
		
		this.pack();
		this.setMinimumSize(this.getSize());
		viewer.autoScale();
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

	}
	
	/*Para controlar el tamaño inicial de cada componente puedes usar el método setPreferredSize.
También necesitarás hacer visible la ventana, etc.
*/
		
}
