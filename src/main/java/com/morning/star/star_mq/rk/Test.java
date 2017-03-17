package com.morning.star.star_mq.rk;

import java.util.HashMap;
import java.util.Map;

public class Test {

	public static void main(String[] args){
		Map<String,Tobj> map = new HashMap<String,Tobj>();
		Tobj tobj = new Test().new Tobj();
		tobj.setName("aaa");
		map.put("1", tobj);
		Temp temp = new Test().new Temp();
		temp.setObj(map.get("1"));
		temp.getObj().setName("bbb");
		System.out.println(map.get("1").getName());
	}
	
	class Temp{
		private Tobj obj;

		public Tobj getObj() {
			return obj;
		}

		public void setObj(Tobj obj) {
			this.obj = obj;
		}
		
	}
	
	class Tobj{
		String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		
	}
}
