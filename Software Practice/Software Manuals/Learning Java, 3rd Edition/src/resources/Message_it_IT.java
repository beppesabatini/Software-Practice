package resources;

import java.util.*;

public class Message_it_IT extends ListResourceBundle {
	
	static final Object[][] contents = {
			{"hello.world", "Salve, mondo"},
			{"bye.for.now", "Ciao"},
	};
	
	public Object[][] getContents(){
		return(contents);
	}
}