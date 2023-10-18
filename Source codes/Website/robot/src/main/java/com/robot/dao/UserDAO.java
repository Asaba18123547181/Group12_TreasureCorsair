package com.robot.dao;

import com.robot.vo.UserInfo;

public interface UserDAO {
	public int queryByUserInfo (UserInfo userinfo, boolean comparePassword) throws Exception;

	void insert(UserInfo userinfo);
}
