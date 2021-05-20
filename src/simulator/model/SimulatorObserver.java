package simulator.model;

import java.util.List;

//TODO: Pregunta muy importante sobre qué es el controlador
/*Interfaz que permite la implementación del patrón Modelo 
Vista Controlador en el simulador físico, la implementación 
de esta interfaz permite notificar a las clases que lo necesiten sobre eventos que ocurren en el simulador.

Permite separar mejor la interfaz del resto del código. Esto se consigue al separarse el modelo, 
actualmente situado en el paquete simulator.model
de el controlador: encargado de los cambios e implementado mediante el 'batchMode' o el 'interfaceMode',
que reciben la entrada de lo que se quiere modificar y al controlador*/
public interface SimulatorObserver {
	public void onRegister(List<Body> bodies, double time, double tReal, String fLawsDesc);
	public void onReset(List<Body> bodies, double time, double tReal, String fLawsDesc);
	public void onBodyAdded(List<Body> bodies, Body b);
	public void onAdvance(List<Body> bodies, double time);
	public void onDeltaTimeChanged(double tReal);
	public void onForceLawsChanged(String fLawsDesc);
}
