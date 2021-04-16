package ie.gmit.sw.ai.nn.activator;

import java.io.Serializable;

public class SigmoidActivator implements Activator, Serializable {
	private static final long serialVersionUID = 1L;

	//f(x) = 1 / (1 + exp( − x))
	public double activate(double value) {
		return (1.0d / (1.0d + Math.exp(-value)));
	}
	
	//Derivative dy/dx = f(x)' = f(x) * (1 - f(x))
	public double derivative(double value) {
		return (value * (1.0d - value));
	}
}