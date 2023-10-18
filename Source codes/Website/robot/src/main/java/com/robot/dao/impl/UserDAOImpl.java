package com.robot.dao.impl;

import com.robot.dao.UserDAO;
import com.robot.db.DBConnect;
import com.robot.vo.UserInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAOImpl implements UserDAO {

	@Override
	public int queryByUserInfo(UserInfo userinfo, boolean comparePassword) throws Exception {
		
		int flag = 0; 
		String sql = "select * from user where username=?";
		PreparedStatement pstmt = null ;

        //针对数据库的具体操作  
        try {
        	//连接数据库
        	pstmt = DBConnect.getConnection().prepareStatement(sql);
        	pstmt.setString(1, userinfo.getUsername());
        	//数据库查询操作
        	ResultSet rs = pstmt.executeQuery();
        	while(rs.next()) {
				if (!comparePassword) {
					return 1;
				}
        		//查询内容并与用户提交内容比较
        		if(rs.getString("password").equals(userinfo.getPassword())) {
					int id = rs.getInt("id");
					userinfo.setId(id);
        			flag = 1;
        		}
        	}
        	rs.close();
        	pstmt.close();
        }catch(SQLException e) {
        	System.out.println(e.getMessage());
        }
		return flag;
	}

	@Override
	public void insert(UserInfo userInfo) {
		try (Connection conn = DBConnect.getConnection();
			 PreparedStatement stmt = conn.prepareStatement("INSERT INTO user (username, password) VALUES (?, ?)")) {

			stmt.setString(1, userInfo.getUsername());
			stmt.setString(2, userInfo.getPassword());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
















