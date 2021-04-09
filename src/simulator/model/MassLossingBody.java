package simulator.model;

import simulator.misc.Vector2D;

/*Cuerpo que pierde cierta cantidad de masa con determinada frecuencia.
 * Comprobado con el archivo cuerpoPierdeMasa.json.
 * lossFactor y lossFrequency son atributos private porque de esta clase no hereda ninguna otra.
 */

public class MassLossingBody extends Body {
    private double lossFactor; //factor de perdida de la masa
    private double lossFrequency; //tiempo despues del cual el objeto pierde masa
    private double counter;

    MassLossingBody(String id, Vector2D velocity, Vector2D position,
			double mass, double lossFactor, double lossFrequency) {
		super(id, velocity, position, mass);
		
		if(lossFactor < 0 || lossFactor > 1) {
			throw new IllegalArgumentException("El factor de pérdida no está en el rango [0,1]");
		}
		
		if(lossFrequency<0) {
			throw new IllegalArgumentException("La frecuencia de pérdida no es positiva");
		}
		
		this.lossFactor = lossFactor;
		this.lossFrequency = lossFrequency;
		this.counter = 0;
	}
	
	public MassLossingBody(Body createTheInstance, double lossFactor, double lossFrequency) {
		this(createTheInstance.id, createTheInstance.velocity, createTheInstance.position,
		createTheInstance.mass, lossFactor, lossFrequency);
	}

	@Override
	void move(double t){
		super.move(t);
		if(counter>=lossFrequency) {
			mass = mass*(1-lossFactor);
			counter = 0;
		}else {
			//tiempo que ha pasado
			counter += t;
		}
	}

}
