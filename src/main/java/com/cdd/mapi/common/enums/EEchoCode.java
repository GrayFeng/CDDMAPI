package com.cdd.mapi.common.enums;

public enum EEchoCode {
	

	SUCCESS{
		public String getCode(){
			return "200";
		}
	},
	NOT_LOGIN{
		public String getCode(){
			return "910";
		}
	},
	MISSING{
		public String getCode(){
			return "204";
		}
	},
	ERROR{
		public String getCode(){
			return "205";
		}
	};
	
	abstract public String getCode();
}
