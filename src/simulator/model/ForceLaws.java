package simulator.model;

import java.util.List;

//Las fuerzas definen el movimiento que tendrá un cuerpo
public interface ForceLaws {
	public void apply(List<Body> bs);
}
