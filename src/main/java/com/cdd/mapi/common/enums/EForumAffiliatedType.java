package com.cdd.mapi.common.enums;
/**
 * CDDMAPI
 * @date 2014-11-2 下午9:27:26
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public enum EForumAffiliatedType {
	LIKE{
		public Integer getCode(){
			return 1;
		}
	},
	FAV{
		public Integer getCode(){
			return 2;
		}
	},
	SHARE{
		public Integer getCode(){
			return 3;
		}
	};
	
	abstract public Integer getCode();
}
