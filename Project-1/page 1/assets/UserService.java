package com.apple.shiro.service;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Service;

import com.apple.shiro.dao.UserDAO;
import com.apple.shiro.group.User;

@Service
public class UserService implements UserServiceAction{
	
	@Resource
	private UserDAO userDao;

	/* (non-Javadoc)
	 * @see com.apple.shiro.service.UserServiceAction#createUser(com.apple.shiro.group.User)
	 */
	@Override
	public void createUser(User user) {
		// TODO Auto-generated method stub
		userDao.create(user);
	}

	/* (non-Javadoc)
	 * @see com.apple.shiro.service.UserServiceAction#getUserDetails(java.lang.String)
	 */
	@Override
	public User getUserDetails(String name)  {
		// TODO Auto-generated method stub
		return  (User)userDao.find(name, User.class.getName());
	}

	/* (non-Javadoc)
	 * @see com.apple.shiro.service.UserServiceAction#deleteUser(java.lang.String)
	 */
	@Override
	public void deleteUser(String name)  {
		// TODO Auto-generated method stub
		System.out.println("Before deleting-------------------------->");
		userDao.delete(name, User.class);
	}

	/* (non-Javadoc)
	 * @see com.apple.shiro.service.UserServiceAction#updateUser(com.apple.shiro.group.User)
	 */
	@Override
	@RequiresRoles("ADMIN")
	public void updateUser(User user)  {
		// TODO Auto-generated method stub
		userDao.update(user);
	}

}
