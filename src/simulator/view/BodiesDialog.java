package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import org.json.JSONObject;

import simulator.control.Controller;

public class BodiesDialog extends JDialog implements ActionListener{
	private static final long serialVersionUID = 1L;
	private static final String title = "Change bodies";
	private Controller _ctrl;
	private ChangeBodiesTableModel _tableModel;
	
	public BodiesDialog(Controller ctrl) {
		super();
		_ctrl = ctrl;
		setTitle(title);	
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocation(300, 200);
		this.setPreferredSize(new Dimension (500,250));
		this.setMinimumSize(this.getPreferredSize());
		this.initGUI();
	}
	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		JPanel buttons = new JPanel();
		JComboBox selectNumberOfBodies;
		ArrayList<Integer> numberArray= new ArrayList<Integer>();
		for(int i=1; i<=4; i++) {
			numberArray.add(i);
		}
	    
		
		//Componente para seleccionar el número de cuerpos
	    selectNumberOfBodies = new JComboBox(numberArray.toArray());
	    _tableModel = new ChangeBodiesTableModel();
	    JTable table = new JTable(_tableModel);

		selectNumberOfBodies.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selection = (int) selectNumberOfBodies.getSelectedItem();
				_tableModel.setRows(selection);
			}
		});
	

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
				//TODO:Se crean los cuerpos con la información de la tabla
				setVisible(false);
			}
		} );
		
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(setDescription("Select number of bodies and their type (default values are used for parametes with no value)."));
		mainPanel.add(scrollPane);
		buttons.add(cancel);
		buttons.add(ok);
		mainPanel.add(selectNumberOfBodies);
		mainPanel.add(buttons);
		this.add(mainPanel);
		this.setVisible(true);
		
		
	}
	private Component setDescription(String string) {
		JTextArea textArea = new JTextArea(string);
		JPanel panel = new JPanel(new BorderLayout());
		textArea.setPreferredSize(new Dimension(500,50));
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		panel.add(textArea, BorderLayout.CENTER);
		return panel;
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
