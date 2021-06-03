package simulator.model;

import simulator.misc.Vector2D;

public class NewthousandtonUniversalGravitation extends NewtonUniversalGravitation implements ForceLaws{
	private double _g;
	private boolean _aplicaConstante;
	public NewthousandtonUniversalGravitation(double g, boolean aplicaConstante) {
		super(g);
		_aplicaConstante = aplicaConstante;
	}
	//Como no se me ocurre nada dependerá directamente de la distancia entre los cuerpos (no inversamente)
	@Override
	protected Vector2D force(Body b1, Body b2) {
		Vector2D dist = b2.getPosition().minus(b1.getPosition());
		double force = ((b1.getMass()*b2.getMass())*(dist.magnitude()*dist.magnitude()));
		if(_aplicaConstante) {
			force = _g*force;
		}
		return dist.direction().scale(force);
	}
	
	@Override
	public String toString() {
		return "Newton Universal Gravitation modified with G: " + _g + _aplicaConstante;
	}
}
