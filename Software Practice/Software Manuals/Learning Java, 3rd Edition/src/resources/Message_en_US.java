package resources;

import java.util.*;

public class Message_en_US extends ListResourceBundle {
	
	static final Object[][] contents = {
			{"hello.world", "Hello, world"},
			{"bye.for.now", "Bye for now"},
	};
	
	public Object[][] getContents(){
		return(contents);
	}
}