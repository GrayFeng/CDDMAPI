package com.cdd.mapi.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cdd.mapi.common.pojo.Result;

/**
 * Description: 登录拦截器
 */
public class RuleInterceptor extends HandlerInterceptorAdapter{


	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler)
			throws Exception {
		String uid = request.getParameter("uid");
		String uri = request.getRequestURI();
		HandlerMethod handlerMethod =  (HandlerMethod)handler;
		Result result = null;
//		if(uri.contains("/gm/")){
//			String adminName = (String)request.getSession().getAttribute("adminName");
//			if(handlerMethod.getMethodAnnotation(NotNeedLogin.class) == null 
//					&& StringUtils.isEmpty(adminName)){
//				String contextPath = request.getContextPath();
//				if(StringUtils.isEmpty(contextPath)){
//					contextPath = "";
//				}
//				response.sendRedirect(contextPath+"/gm/index.do");
//				return false;
//			}
//		}else{
//			if(handlerMethod.getMethodAnnotation(NotNeedUID.class) == null 
//					&& (StringUtils.isEmpty(uid) || !MemberCache.getInstance().isHave(uid))){
//				result = new Result(EEchoCode.ERROR.getCode(),"缺少UID认证信息");
//			}else if(handlerMethod.getMethodAnnotation(NotNeedLogin.class) == null 
//					&& !MemberCache.getInstance().isLogin(uid)){
//					result = new Result(EEchoCode.NOT_LOGIN.getCode(),"用户未登录");
//			}
//			if(result != null){
//				response.setContentType("text/plain;charset=UTF-8");  
//				OutputStream os = response.getOutputStream();
//				os.write(ResultUtil.getJsonString(result).getBytes(Charset.forName("UTF-8")));
//				os.flush();
//				os.close();
//				response.flushBuffer();
//				return false;
//			}
//		}
		return true;
	}
}
