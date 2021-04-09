package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {
	private double aceleration;
	Vector2D fixedPoint;
	
	public MovingTowardsFixedPoint(double g) {
		aceleration = g;
		fixedPoint = new Vector2D();
	}

	
	@Override
	public void apply(List<Body> bodies) {
		for (Body b : bodies) {
			Vector2D dir = fixedPoint.minus(b.getPosition()).direction();
			b.setForce(dir.scale(aceleration*b.getMass()));
		}
	}
	
	public String toString() {
		return "Moving Towards Fixed Point ";
	}
}
