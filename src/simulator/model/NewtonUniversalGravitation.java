package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws{
	public static double GRAVITY = 6.67*Math.pow(10,-11);
	
	private Vector2D force(Body b1, Body b2) {
		double difPositionsEsc = Math.abs(b2.getPosition().magnitude()-b1.getPosition().magnitude());
		Vector2D direction = b2.getPosition().minus(b1.getPosition());
		double force = GRAVITY*((b1.getMass()*b2.getMass())/Math.pow(difPositionsEsc,2));
		return direction.scale(force);
		
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
		}
		
	}
	
	public String toString() {
		return "Newton universal laws";
	}
	
}
