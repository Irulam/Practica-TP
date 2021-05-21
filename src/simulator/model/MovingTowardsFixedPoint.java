package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{
	private double g = 9.81;
	private 
	@Override
	public void apply(List<Body> bodies) {
		for (Body b : bodies) {
			Vector2D dir = b.getPosition().direction();
			b.setForce(dir.scale(-ACCELERATION*b.getMass()));
		}

		
	}
	//TODO: cambiar la string cuando esté bien hecho este método
	public String toString() {
		return "Moving Towards " + c +" with constant acceleration "+ g;
	}
}
