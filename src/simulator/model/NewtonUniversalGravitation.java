package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {
	private double _g;
	
	public NewtonUniversalGravitation(double g) {
		_g = g;
	}
	
	private Vector2D force(Body b1, Body b2) {
		Vector2D dist = b2.getPosition().minus(b1.getPosition());
		double force = _g*((b1.getMass()*b2.getMass())/(dist.magnitude()*dist.magnitude()));
		return dist.direction().scale(force);
	}
	
	@Override
	public void apply(List<Body> bodies) {
		for(Body b1: bodies) {
			if (b1.getMass() == 0.0) {
				b1.resetForce();
				b1.resetVelocity();
			} else {
				Vector2D fab = new Vector2D();
				
				for(Body b2:bodies) {
					if(!b1.equals(b2)) {
						fab = fab.plus(force(b1,b2));				
					}
				}
				
				b1.setForce(fab);
			}
		}
	}
	
	public String toString() {
		return "Newton Universal Gravitation with G: " + _g;
	}
	
}
