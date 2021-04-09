package simulator.model;

import simulator.misc.Vector2D;

public class MassLossingBody extends Body {
    private double lossFactor; //factor de perdida de la masa
    private double lossFrequency; //tiempo despues del cual el objeto pierde masa
    private double counter; 
	protected double mass;
    MassLossingBody(String id, Vector2D velocity, Vector2D position,
			double mass, double lossFactor, double lossFrequency) {
		super(id, velocity, position, mass);
		if(lossFactor<0 || lossFactor>1) {
			throw new IllegalArgumentException("El factor de pérdida no está en el rango 0-1");
		}
		this.lossFactor = lossFactor;
		this.lossFrequency = lossFrequency;
		this.counter = 0;
	}
	
	public MassLossingBody(Body createTheInstance, double lossFrequency2, double lossFactor2) {
		super(createTheInstance);
	}

	void move(double t){
		super.move(t);
		if(counter>=lossFrequency) {
			mass = mass*(1-lossFactor);
			counter = 0;
		}else {
			//tiempo que ha pasado
			counter+=t;
		}
	}

}
