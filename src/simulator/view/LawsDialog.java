package simulator.view;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.json.JSONObject;

import simulator.control.Controller;


public class LawsDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final String title = "Change gravity laws";
	private Controller _ctrl;
	private LawsTableModel _tableModel;
	
	public LawsDialog(Controller ctrl) {
		super();
		_ctrl = ctrl;
		setTitle(title);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocation(300, 200);
		this.setPreferredSize(new Dimension (200,150));
		this.initGUI();
	}

	protected void initGUI() {
		JPanel mainPanel = new JPanel();
		JPanel buttons = new JPanel();
		JComboBox selectForce;
	    mainPanel.setLayout(new GridLayout(3,1));
	    
	    //Lista de leyes de fuerza
		ArrayList<String> options = new ArrayList<>();
		for (JSONObject jo : _ctrl.getForceLawsInfo()) {
			options.add(jo.getString("desc"));
		}
		
		//Componente para seleccionar el tipo de fuerza
	    selectForce = new JComboBox(options.toArray());
	    _tableModel = new LawsTableModel(_ctrl.getForceLawsInfo().get(0));
	    JTable table = new JTable(_tableModel);

		selectForce.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Action performed");
				String selection = (String) selectForce.getSelectedItem();
				
				for (JSONObject jo: _ctrl.getForceLawsInfo()) {
					if (jo.getString("desc").equals(selection)) {
						_ctrl.setForceLaws(jo);
						_tableModel.setInfo(jo);
					}
				}
			}
		});
	
		/*
		String response = (String) JOptionPane.showInputDialog(this, "Select gravity laws to be used.",
				"Gravity Laws Selector", JOptionPane.PLAIN_MESSAGE, null, options.toArray(), null);
  
  */

		JButton cancel= new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
			}
		} );
		
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(scrollPane);
		buttons.add(cancel);
		buttons.add(ok);
		mainPanel.add(selectForce);
		mainPanel.add(buttons);
		this.add(mainPanel);
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}