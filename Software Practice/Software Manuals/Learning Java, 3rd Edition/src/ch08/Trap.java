package ch08;

// Type variables are introduced on p. 226-227
class Mouse {
}

class Bear {
}

class Trap<T> {
	T trapped;

	public void snare(T trapped) {
		this.trapped = trapped;
	}

	public T release() {
		return trapped;
	}
}

class TrapMain {

	static <T> Trap<T> create() {
		return new Trap<T>();
	}

	public static void main(String[] ar) {
		Trap<Mouse> mouseTrap = new Trap<Mouse>();
		mouseTrap.snare(new Mouse());
		Mouse mouse = mouseTrap.release();
		System.out.println("Set free: " + mouse.getClass().getName());

		// Type Inference from Assignment Context appears no p. 241
		
		// Type inference of factory method
		Trap<Mouse> mouseTrap2 = create();
		// mouseTrap.snare(new Bear()); won't compile

		// Kill warning
		if(mouseTrap2 != null) {
			mouseTrap2 = null;
		}
		
		Trap<Bear> bearTrap = create();
		// if(bearTrap instanceof Trap<Bear>) { won't compile
		if(bearTrap instanceof Trap<?>) {
			System.out.println("The instanceof operator only supports wildcards");
		}
	}
}
