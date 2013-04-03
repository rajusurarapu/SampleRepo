package com.apple.shiro.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

public class MySuccesLoginFilter extends FormAuthenticationFilter {
	
	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onAccessDenied(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 * 
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("u889u9u0ue90ur9ewu09ru9ew0u90weu9urwe9ur9weu90ruew90u90weur90uew9ru9ewur908ewur8ew908tuy8ewyt8y");
		return super.onAccessDenied(request, response);
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#getFailureKeyAttribute()
	 */
	@Override
	public String getFailureKeyAttribute() {
		// TODO Auto-generated method stub
		System.out.println("!!!!!!!!!!!!!!!!!");
		return super.getFailureKeyAttribute();
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#getRememberMeParam()
	 */
	@Override
	public String getRememberMeParam() {
		// TODO Auto-generated method stub
		System.out.println("22222222222222222");
		return super.getRememberMeParam();
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#isRememberMe(javax.servlet.ServletRequest)
	 */
	@Override
	protected boolean isRememberMe(ServletRequest request) {
		// TODO Auto-generated method stub
		System.out.println("3333333");
		return super.isRememberMe(request);
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginFailure(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.authc.AuthenticationException, javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("777777777777777");
		return super.onLoginFailure(token, e, request, response);
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginSuccess(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.subject.Subject, javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		System.out.println("$$$$$$$$$$$$$$$$$$$$");
		// TODO Auto-generated method stub
		super.onLoginSuccess(token, subject, request, response);
		WebUtils.issueRedirect(request, response, getSuccessUrl());
		return false;
	}
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		System.out.println("ENTERINIG redirectToLogin OF MySuccesLoginFilter");
		org.apache.shiro.subject.Subject sub = null;
		String userName = request.getParameter("username");
		System.out.println(" the user name is -->"+userName);
		try{
		if(userName == null || userName.isEmpty()){
			 sub = SecurityUtils.getSubject();
			userName = (String) sub.getPrincipals().fromRealm("com.apple.shiro.relam.MyShiroRealm_0").iterator().next();
			System.out.println(" the user name is getPrincipals-->"+userName);
		}
			/*if(!userName.equalsIgnoreCase("ADMIN")){*/
				String password = request.getParameter("password");
				UsernamePasswordToken token = new UsernamePasswordToken(userName.toUpperCase(), password);
				try
				{
					SecurityUtils.getSubject().login(token);
				} catch (AuthenticationException e) {
					System.out.println("The username or password was not correct.");
					e.printStackTrace();
				}
				sub = SecurityUtils.getSubject();
				if(sub.isAuthenticated()){
					System.out.println("the user is autheticated");
				}
				if(sub.isAuthenticated()){
					System.out.println("User Authenticated");
					WebUtils.issueRedirect(request, response, getSuccessUrl());
				}else{

				}
		}catch(Exception e){
			e.printStackTrace();
			WebUtils.issueRedirect(request, response, "/unauthorized.jsp");
		}
				/*}else{
			UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
			try
			{
				SecurityUtils.getSubject().login(token);
			} catch (AuthenticationException e) {
				System.out.println("The username or password was not correct.");
				e.printStackTrace();
			}
			WebUtils.issueRedirect(request, response, getSuccessUrl());
		}*/
		}

}
