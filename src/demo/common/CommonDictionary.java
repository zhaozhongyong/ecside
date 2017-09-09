package demo.common;

import java.util.LinkedHashMap;
import java.util.Map;


public class CommonDictionary {
	public static Map GENDER;
	public static Map USERROLE;
	
	static{
		GENDER=new LinkedHashMap();
		GENDER.put("0", "\u672A\u77E5");
		GENDER.put("1", "\u7537");
		GENDER.put("2", "\u5973");
		
		USERROLE=new LinkedHashMap();
		USERROLE.put("0", "\u9501\u5B9A");
		USERROLE.put("1", "\u666E\u901A");
		USERROLE.put("2", "\u9AD8\u7EA7");
		USERROLE.put("3", "\u7BA1\u7406\u5458");
		USERROLE.put("4", "\u8D85\u7EA7\u7BA1\u7406\u5458");
		
	}

	
}
