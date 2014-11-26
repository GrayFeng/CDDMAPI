package com.cdd.mapi.common;

/**
 * Created with IntelliJ IDEA.
 * User: GrayF
 * Date: 14-11-26
 * Time: 上午11:11
 * To change this template use File | Settings | File Templates.
 */
public class RequestContext {

    private static ThreadLocal<RequestLocal> requestLocal = new ThreadLocal<RequestLocal>();

    public static void init(){
        requestLocal.set(new RequestLocal());
    }

    public static String getUID() {
        return (requestLocal.get() != null) ? requestLocal.get().getUid() : null;
    }

    public static void setUID(String uid) {
        requestLocal.get().setUid(uid);
    }

    private static class RequestLocal{
        private String uid;

        private String getUid() {
            return uid;
        }

        private void setUid(String uid) {
            this.uid = uid;
        }
    }
}
