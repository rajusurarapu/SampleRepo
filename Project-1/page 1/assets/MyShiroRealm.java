package com.apple.shiro.relam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apple.shiro.dao.UserDAO;
import com.apple.shiro.dao.UserDao_Bkp;
import com.apple.shiro.group.Group;
import com.apple.shiro.group.User;

/*
 * Realms are responsible for authentication and authorization. Any time user wants to log in to the application, authentication information
 * is collected and passed to realm.Realm verifies supplied data and decides whether user should be allowed to log in, have access to resource
 * or own specific role.
 * Authentication : principal - represents account unique identifier e.g. user name, account id
 * credential - proves users identity e.g. password
 */
public class MyShiroRealm extends AuthorizingRealm {

	@Autowired
	private UserDAO userDAO;

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		System.out.println("Entering -------------->doGetAuthenticationInfo");

		org.apache.shiro.subject.Subject  sub = null;
		User usr =null;
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String userNme = token.getUsername();
		token.setRememberMe(true);
		if(userNme ==  null || (userNme.trim()).isEmpty()){
			System.out.println("UserName and Password is Empty :-Throwing excecpiton----->");
			throw new AuthenticationException();
		}
		else{
			sub = SecurityUtils.getSubject();
			Session ses = sub.getSession(true);
			
			if(userNme.equalsIgnoreCase("ADMIN")){
				usr = new User();
				usr.setName("ADMIN");
				usr.setPermission("USER:READ,WRITE,EDIT,DELETE");
				usr.setRole("ADMIN");
				User  usr1 = (User) userDAO.find(userNme, User.class.getName());
				if(usr1 == null)
					userDAO.create(usr);
				System.out.println("ADMIN user created successfully");
			}else{
				usr = (User) userDAO.find(userNme, User.class.getName());
			}

			System.out.println("Proceeding after Amdim is created----->");

			if(usr!=null && !usr.getName().equalsIgnoreCase(userNme)){
				throw new AuthenticationException();
			}

			return (new SimpleAuthenticationInfo(userNme,token.getPassword(),getName()));
		}
	}
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String loginName = (String) principals.fromRealm(getName()).iterator().next();
		User myUser = (User) userDAO.find(loginName, User.class.getName());
		//User myUser = dao.getUser(loginName);
		System.out.println("%%%%%%%%%%% doGetAuthorizationInfo-> Called for authorized verifcation "+loginName);
		if (myUser != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.addStringPermission(myUser.getPermission());
			info.addRole(myUser.getRole());
			return info;
		} else {
			System.out.println("Unable to locate user from DB");
			return null;
		}
	}

	/**
	 * @return the userDAO
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}
	/**
	 * @param userDAO the userDAO to set
	 */

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

}
