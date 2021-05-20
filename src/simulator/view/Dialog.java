package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Dialog extends JDialog implements ActionListener {


	private static final long serialVersionUID = 1L;
	private String definition;
	
	public Dialog(String title, String definition) {
		this.definition=definition;
		setTitle(title);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocation(300, 200);
		this.setPreferredSize(new Dimension (450,150));
	}

	protected void initGUI() {
		JPanel mainPanel = new JPanel();
	    mainPanel.setLayout(new GridLayout(3,1));
	    mainPanel.add(title());
		mainPanel.add(continuarProceso());
		this.add(mainPanel);
 		this.setVisible(true); 
	    this.pack();
	}


	private Component continuarProceso() {
		JPanel butonpanel= new JPanel(new FlowLayout());
		butonpanel.setBackground(null);
		JButton cancel= new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		JButton ok = new JButton("Ok");
		ok.addActionListener(this );
		butonpanel.add(cancel);
		butonpanel.add(ok);
		return butonpanel;
	}

	private Component title() {
		 JPanel infor = new JPanel(new BorderLayout());
			JTextArea info = new JTextArea(this.definition);
			info.setPreferredSize(new Dimension(400, 30));
			info.setBackground(null);
			info.setEditable(false);
			info.setLineWrap(true);
			info.setWrapStyleWord(true);
			infor.add(info,BorderLayout.CENTER);
		return infor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


}