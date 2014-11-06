package com.cdd.mapi.common.enums;

public enum EUploadType {
	
	MEMBER_PHOTO{
		public Integer getCode(){
			return 100;
		}
	},
	FORUM_PHOTO{
		public Integer getCode(){
			return 101;
		}
	},
	COF_PHOTO{
		public Integer getCode(){
			return 102;
		}
	};
	
	abstract public Integer getCode();
}
