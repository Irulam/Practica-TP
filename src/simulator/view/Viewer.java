package simulator.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class Viewer extends JComponent implements SimulatorObserver {
	private static final int CROSS_RADIUS = 5;
	private static final int BODY_TAM = 5;
	private static final int VECTOR_LENGTH = 20;
	private static final int ARROW_W = 3;
	private static final int ARROW_H = 3;

	private int _centerX;
	private int _centerY;
	private double _scale;
	private List<Body> _bodies;
	private boolean _showHelp;
	private boolean _showVectors;
	
	Viewer(Controller ctrl){
		_bodies = new ArrayList<>();
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		// TODO add border with title
		_scale = 1.0;
		_showHelp = true;
		_showVectors = true;
		setBorder(BorderFactory.createTitledBorder("Viewer"));
		addKeyListener(new KeyListener() {
			//...
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyChar()) {
					case '-':
						_scale = _scale * 1.1;
						repaint();
						break;
					case '+':
						_scale = Math.max(1000.0, _scale/1.1);
						repaint();
						break;
					case '=':
						autoScale();
						repaint();
						break;
					case 'h':
						_showHelp = !_showHelp;
						repaint();
						break;
					case 'v':
						_showVectors = !_showVectors;
						 repaint();
						break;
					default:
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		addMouseListener(new MouseListener(){
			//..
			@Override
			public void mouseEntered(MouseEvent e) {
				requestFocus();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// use ’gr’ to draw not ’g’ --- it gives nicer results
		Graphics2D gr = (Graphics2D) g;
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		_centerX = getWidth() / 2;
		_centerY = getHeight() / 2;
		
		
		//Dibuja cada uno de los cuerpos actuales
		for (Body body : _bodies) {
			double x = body.getPosition().getX();
			double y = body.getPosition().getY();
			
			//Obtiene los vectores velocidad y fuerza de los cuerpos a dibujar
			int u = (int) (body.getVelocity().direction().getX()*VECTOR_LENGTH);
			int v = (int) (body.getVelocity().direction().getY()*VECTOR_LENGTH);
			int w = (int) (body.getForce().direction().getX()*VECTOR_LENGTH);
			int t = (int) (body.getForce().direction().getY()*VECTOR_LENGTH);
			
			//Traza los vectores
			if (!Double.isNaN(x) && !Double.isNaN(y)) {
				int scaledx = _centerX + (int)(x/_scale); 
				int scaledy = _centerY - (int) (y/_scale);
				if (_showVectors) {
					gr.setStroke(new BasicStroke(2));
					drawLineWithArrow(gr,scaledx, scaledy, scaledx+u, scaledy-v, ARROW_W, ARROW_H, Color.GREEN, Color.GREEN);
					drawLineWithArrow(gr,scaledx, scaledy, scaledx+w, scaledy-t, ARROW_W, ARROW_H, Color.RED, Color.RED);
				}
				
				//Dibuja los cuerpos como tal
				gr.setColor(Color.BLACK);
				gr.drawString(body.getId(), scaledx-gr.getFontMetrics().stringWidth(body.getId())/2, scaledy-2*BODY_TAM);
				gr.setColor(Color.BLUE);
				gr.fillOval(scaledx-BODY_TAM, scaledy-BODY_TAM, BODY_TAM*2, BODY_TAM*2);
			}
		}
		
		gr.setColor(Color.RED);
		
		gr.setStroke(new BasicStroke(1));
		gr.drawLine(_centerX - CROSS_RADIUS, _centerY, _centerX + CROSS_RADIUS, _centerY);
		gr.drawLine(_centerX, _centerY - CROSS_RADIUS, _centerX, _centerY + CROSS_RADIUS);
		
		if(_showHelp) {
			int paddingLeft = getBorder().getBorderInsets(this).left + 5;
			int lineHeight = gr.getFontMetrics().getAscent() + 2;
			int paddingTop = getBorder().getBorderInsets(this).top + lineHeight;
			gr.drawString("h:toggle help, v:toggle vectors,+:zoom-in,-:zoom_out,=:fit", paddingLeft, paddingTop);
			gr.drawString("Scaling ratio: " + String.valueOf(_scale), paddingLeft, paddingTop + lineHeight);
		}
	}
	
	void autoScale() {
		double max = 1.0;
		for (Body b : _bodies) {
			Vector2D p = b.getPosition();
			max = Math.max(max, Math.abs(p.getX()));
			max = Math.max(max, Math.abs(p.getY()));
		}
		double size = Math.max(1.0, Math.min(getWidth(), getHeight()));
		_scale = max > size ? 4.0 * max / size : 1.0;
	}
	
	// This method draws a line from (x1,y1) to (x2,y2) with an arrow.
	// The arrow is of height h and width w.
	// The last two arguments are the colors of the arrow and the line
	private void drawLineWithArrow(Graphics g, int x1, int y1, int x2, int y2, int w, int h, Color lineColor, Color arrowColor) {
		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx * dx + dy * dy);
		double xm = D - w, xn = xm, ym = h, yn = -h, x;
		double sin = dy / D, cos = dx / D;
		x = xm * cos - ym * sin + x1;
		ym = xm * sin + ym * cos + y1;
		xm = x;
		x = xn * cos - yn * sin + x1;
		yn = xn * sin + yn * cos + y1;
		xn = x;
		int[] xpoints = { x2, (int) xm, (int) xn };
		int[] ypoints = { y2, (int) ym, (int) yn };
		g.setColor(lineColor);
		g.drawLine(x1, y1, x2, y2);
		g.setColor(arrowColor);
		g.fillPolygon(xpoints, ypoints, 3);
	}
	
	private void updateAndRepaint(List<Body> bodies) {
		_bodies = bodies;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				autoScale();
				repaint();
			}
		});
	}
	
	@Override
	public void onRegister(List<Body> bodies, double time, double tReal, String fLawsDesc) {
		updateAndRepaint(bodies);
	}

	@Override
	public void onReset(List<Body> bodies, double time, double tReal, String fLawsDesc) {
		updateAndRepaint(bodies);
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		updateAndRepaint(bodies);
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				repaint();
			}
		});

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
