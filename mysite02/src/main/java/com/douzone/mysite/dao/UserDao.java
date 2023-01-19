package com.douzone.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douzone.mysite.vo.UserVo;

public class UserDao {
	public void insert(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			// 3. Statement 준비
			String sql = "insert into user values(null, ?, ?, password(?), ?, now())";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			// 4. SQL 실행
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error: " + e);;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			// 1. JDBC Driver Class 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://192.168.10.121:3307/webdb?charset=utf8";   		
			// DriverManager의 Connection 불러오기
			// 2. 드라이버 연결하기
			conn = DriverManager.getConnection(url, "webdb", "webdb");  // (url, 아이디, 비밀번호)
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}
		
		return conn;
	}
	
}
