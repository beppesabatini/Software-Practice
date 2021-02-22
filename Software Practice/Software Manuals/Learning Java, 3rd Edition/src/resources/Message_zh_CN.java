package resources;

import java.util.ListResourceBundle;

public class Message_zh_CN extends ListResourceBundle {
	
	static final Object[][] contents = {
			{"hello.world", "你好，世界"},
			{"bye.for.now", "暂时再见"},
	};
	
	public Object[][] getContents(){
		return(contents);
	}
}
