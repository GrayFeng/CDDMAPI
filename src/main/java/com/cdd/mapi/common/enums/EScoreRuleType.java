package com.cdd.mapi.common.enums;

public enum EScoreRuleType {
	
	SIGN{
		public Integer getCode(){
			return 1;
		}
	};
	
	abstract public Integer getCode();
}
