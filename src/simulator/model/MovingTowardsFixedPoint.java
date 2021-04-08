package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{
	private static final double ACCELERATION = 9.81;
	@Override
	public void apply(List<Body> bodies) {
		for (Body b : bodies) {
			Vector2D dir = b.getPosition().direction();
			b.setForce(dir.scale(-ACCELERATION*b.getMass()));
		}

		
	}
	
	public String toString() {
		return "Moving Towards Fixed Point ";
	}
}
