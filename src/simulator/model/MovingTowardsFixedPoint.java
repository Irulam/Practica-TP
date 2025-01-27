package simulator.model;

import java.util.Arrays;
import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{
	private double g = 9.81;
	private double c [] = new double [] {0.0};
	public MovingTowardsFixedPoint(double g, double[] c) {
		this.g = g;
		this.c = c;
	}
	@Override
	public void apply(List<Body> bodies) {
		for (Body b : bodies) {
			Vector2D dir = b.getPosition().direction();
			b.setForce(dir.scale(-g*b.getMass()));
		}

		
	}
	
	public String toString() {
		return "Moving Towards " + Arrays.toString(c) +" with constant acceleration "+ g;
	}
}
