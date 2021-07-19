package support;

import java.io.Serializable;

public abstract class Mixer implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name = "default";

	// algorithm, that takes an arrays of integer values representing
	// colors of lights and modifies them in some way
	public abstract void mix(int[] lightings);

	// delivers mixer name
	public String getName() {
		return name;
	}

	public Mixer(String name) {
		this.name = name;
	}

}
