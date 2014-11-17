package com.cdd.mapi.common.enums;

public enum EScoreRuleType {
	
	SIGN{
		public Integer getCode(){
			return 1;
		}
	},
	ADD_SUBJECT{
		public Integer getCode(){
			return 2;
		}
	},
	ANSWER_QUESTION{
		public Integer getCode(){
			return 3;
		}
	},
	LIKE_SUBJECT{
		public Integer getCode(){
			return 4;
		}
	},
	SHARE_SUBJECT{
		public Integer getCode(){
			return 5;
		}
	},
	ADD_NEWS{
		public Integer getCode(){
			return 6;
		}
	},
	REPLY_NEWS{
		public Integer getCode(){
			return 7;
		}
	},
	LIKE_NEWS{
		public Integer getCode(){
			return 8;
		}
	},
	SHARE_NEWS{
		public Integer getCode(){
			return 9;
		}
	};
	
	abstract public Integer getCode();
}
