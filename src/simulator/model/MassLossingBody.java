package simulator.model;

import simulator.misc.Vector2D;

//TODO: inventar una clase nueva de cuerpos como esta
public class MassLossingBody extends Body {
    double lossFactor;
    double lossFrequency; //tiempo despues del cual el objeto pierde masa
    double counter; //TODO: cómo se va cambiando
	private double mass;
    MassLossingBody(String id, Vector2D velocity, Vector2D force, Vector2D position,
			double mass, double lossFactor, double lossFrequency) {
		super(id, velocity, force, position, mass);
		this.lossFactor = lossFactor;
		this.lossFrequency = lossFrequency;
		this.counter = 0;
	}
	
	public MassLossingBody(Body createTheInstance, double lossFrequency2, double lossFactor2) {
		super(createTheInstance);
	}

	void move(double t){
		super.move(t);
		if(counter==lossFrequency) {
			mass = mass*(1-lossFactor);
			counter = 0;
		}else {
			counter++;
		}
	}

}
