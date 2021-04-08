package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws{
	public static double GRAVITY = 6.67e-11;
	
	private Vector2D force(Body b1, Body b2) {
		Vector2D dist = b2.getPosition().minus(b1.getPosition());
		double force = GRAVITY*((b1.getMass()*b2.getMass())/(dist.magnitude()*dist.magnitude()));
		return dist.direction().scale(force);
	}
	
	@Override
	public void apply(List<Body> bodies) {
		for(Body b1: bodies) {
			Vector2D fab = new Vector2D();
			
			for(Body b2:bodies) {
				if(!b1.equals(b2)) {
					fab = fab.plus(force(b1,b2));				
				}
			}
			
			if (b1.getMass() == 0.0) {
				b1.setForce(new Vector2D());
			} else {
				b1.setForce(fab);
			}
		}
	}
	
	public String toString() {
		return "Newton universal laws";
	}
	
}
