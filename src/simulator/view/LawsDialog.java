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

import org.json.JSONObject;

import simulator.control.Controller;


public class LawsDialog extends JDialog implements ActionListener {


	private static final long serialVersionUID = 1L;
	private static final String title = "Change gravity laws";
	private Controller _ctrl;
	
	public LawsDialog(Controller ctrl) {
		_ctrl = ctrl;
		setTitle(title);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocation(300, 200);
		this.setPreferredSize(new Dimension (200,150));
	}

	protected void initGUI() {
		JPanel mainPanel = new JPanel();
		JComboBox selectForce;
	    mainPanel.setLayout(new GridLayout(3,1));
	    
	    
	    
	    //Lista de leyes de fuerza
		ArrayList<String> options = new ArrayList<>();
		for (JSONObject jo : _ctrl.getForceLawsInfo()) {
			options.add(jo.getString("desc"));
		}
		//Componente para seleccionar el tipo de fuerza
	    selectForce = new JComboBox(options.toArray());
	   
		/*
		String response = (String) JOptionPane.showInputDialog(this, "Select gravity laws to be used.",
				"Gravity Laws Selector", JOptionPane.PLAIN_MESSAGE, null, options.toArray(), null);
  
  */
	    String selection = (String) selectForce.getSelectedItem();
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

				// Tres opciones para pasar como options al inputDialog:
				// 1. Usar ForceLaws -> Tienes que crear un metodo setForceLaws(ForceLaws) en lugar de JSON
				// 2. Usar JSONObject -> ??? -> Pasar JSONObject
				// 3. Usar Strings? -> Hace falta convertir luego de String a JSONObject igualmente
				
				for (JSONObject jo: _ctrl.getForceLawsInfo()) {
					if (jo.getString("desc").equals(selection)) {
						_ctrl.setForceLaws(jo);
					}
				}
			
			}
		} );
		
		mainPanel.add(selectForce);
		this.add(mainPanel);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}