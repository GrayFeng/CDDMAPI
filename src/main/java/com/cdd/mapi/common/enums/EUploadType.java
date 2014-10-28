package com.cdd.mapi.common.enums;

public enum EUploadType {
	
	MEMBER_PHOTO{
		public Integer getCode(){
			return 100;
		}
	},
	PRIZE_PHOTO{
		public Integer getCode(){
			return 101;
		}
	};
	
	abstract public Integer getCode();
}
