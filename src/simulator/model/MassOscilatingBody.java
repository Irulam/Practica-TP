package simulator.model;

import simulator.misc.Vector2D;

public class MassOscilatingBody extends Body{
	private double oscilatingFactor;
	
	public MassOscilatingBody(String id, Vector2D velocity, Vector2D position, double mass, double oscilatingFactor) {
		super(id, velocity, position, mass);
		this.oscilatingFactor = oscilatingFactor;
	}
	public MassOscilatingBody(Body createTheInstance, double oscilatingFactor) {
		this(createTheInstance.id, createTheInstance.velocity, createTheInstance.position,
		createTheInstance.mass, oscilatingFactor);
	}
	
	@Override
	void move(double t){
		super.move(t);
		mass = mass + Math.pow(-1, t)*oscilatingFactor*mass;
	}
}
