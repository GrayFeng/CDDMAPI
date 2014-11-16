package com.cdd.mapi.common.enums;

public enum ERemindType {
	
	EXAM{
		public Integer getCode(){
			return 1;
		}
	},
	SIGN_UP{
		public Integer getCode(){
			return 2;
		}
	},
	LEARING{
		public Integer getCode(){
			return 3;
		}
	};
	
	abstract public Integer getCode();
}
