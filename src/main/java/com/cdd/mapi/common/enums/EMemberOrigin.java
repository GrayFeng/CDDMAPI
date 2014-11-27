package com.cdd.mapi.common.enums;

/**
 * Created with IntelliJ IDEA.
 * User: GrayF(jy.feng@zuche.com))
 * Date: 14-11-27
 * Time: 上午9:18
 */
public enum EMemberOrigin {

    APP{
        public Integer getCode(){
            return 1;
        }
    },
    QQ{
        public Integer getCode(){
            return 2;
        }
    };

    abstract public Integer getCode();
}
