package com.cdd.mapi.common.enums;

/**
 * Description: ENoticeType.java
 * All Rights Reserved.
 * @version 1.0  2014年11月17日 上午9:23:17  
 * @author Gray(jy.feng@zuche.com) 
 */

public enum ENoticeType {

	INDEX{
		public Integer getCode(){
			return 1;
		}
	},
	REMIND{
		public Integer getCode(){
			return 2;
		}
	};
	
	abstract public Integer getCode();
}
