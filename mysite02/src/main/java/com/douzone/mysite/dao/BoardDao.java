package com.douzone.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.douzone.mysite.vo.BoardVo;

public class BoardDao {
	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			// 3. Statement 준비
			String sql = "select a.no, a.title, a.hit, date_format(a.reg_date, '%Y-%m-%d %H:%i:%s'), b.name, a.depth" 
			+ " from board a, user b" 
			+ " where a.user_no = b.no"
			+ " order by a.g_no desc, a.o_no asc";
			pstmt = conn.prepareStatement(sql);
			
			// 4. SQL 실행
			rs = pstmt.executeQuery(); 
			
			// 5. 결과 처리
			while (rs.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setHit(rs.getLong(3));
				vo.setRegDate(rs.getString(4));
				vo.setUserName(rs.getString(5));
				vo.setDepth(rs.getLong(6));

				result.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("error: " + e);;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
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
		
		return result;
	}
	
	public List<BoardVo> findKeyword(String keyword) {
		List<BoardVo> result = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		

		try {
			conn = getConnection();

			// 3. Statement 준비
			String sql = "select a.no, a.title, a.hit, date_format(a.reg_date, '%Y-%m-%d %H:%i:%s'), b.name, a.depth" 
					+ " from board a, user b" 
					+ " where a.title LIKE '%" + keyword + "%' order by a.g_no desc, a.o_no asc";
		
			pstmt = conn.prepareStatement(sql);
			
			// 4. SQL 실행
			rs = pstmt.executeQuery(); 
			
			// 5. 결과 처리
			while (rs.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setHit(rs.getLong(3));
				vo.setRegDate(rs.getString(4));
				vo.setUserName(rs.getString(5));
				vo.setDepth(rs.getLong(6));

				result.add(vo);
				System.out.println(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("error: " + e);;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
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
		
		return result;
	}
	
	public BoardVo findView(Long no) {
		BoardVo result = null;
		 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			// 3. Statement 준비
			String sql = "select a.no, a.title, a.contents, b.name" 
			+ " from board a, user b" 
			+ " where a.user_no = b.no"
			+ " and a.no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			// 4. SQL 실행
			rs = pstmt.executeQuery(); 
			
			// 5. 결과 처리
			if(rs.next()) {
				result = new BoardVo();
				result.setNo(rs.getLong(1));
				result.setTitle(rs.getString(2));
				result.setContents(rs.getString(3));
				result.setUserName(rs.getString(4));
			}
			
		} catch (SQLException e) {
			System.out.println("error: " + e);;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
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
		
		return result;
	}

	public void insert(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement selectpstmt = null;
		PreparedStatement updatepstmt = null;
		ResultSet rs = null;
		String sql = null;
		String selectSql = null;
		String updateSql = null;
		int groupNo = 0;
		int orderNo = 0;
		int depth = 0;
		
		try {
			conn = getConnection();
			
			// 새로운 글쓰기
			if(vo.getNo() == 0) {
				// 3. Statement 준비
				sql = "insert into board (title, contents, hit, reg_date, g_no, o_no, depth, user_no)"
					+ " select ?, ?, 0, now(), ifnull(max(g_no), 0) + 1, 1, 0, ?"
					+ " from board";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContents());
				pstmt.setLong(3, vo.getUserNo());
		
				// 4. SQL 실행
				pstmt.executeUpdate();
				
				// 댓글 쓰기
			} else {
				// 부모 글의 g_no, o_no, depth 찾기
				selectSql = "select g_no, o_no, depth from board where no = ?";
				selectpstmt = conn.prepareStatement(selectSql);
				
				selectpstmt.setLong(1, vo.getNo());
				rs = selectpstmt.executeQuery();
			
				if(rs.next()) {
					groupNo =rs.getInt(1);
					orderNo =rs.getInt(2) + 1;
					depth =rs.getInt(3) + 1;
				}
				
				//update
				updateSql="update board set o_no = o_no + 1 where g_no = ? and o_no >= ?";
				updatepstmt = conn.prepareStatement(updateSql);
				
				updatepstmt.setInt(1, groupNo);
				updatepstmt.setInt(2, orderNo);
				updatepstmt.executeQuery();
				
				//insert
				sql = "insert into board(title, contents, hit, reg_date, g_no, o_no, depth, user_no)"
					+ " values(?, ?, 0, now(), ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
					
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContents());
				pstmt.setInt(3, groupNo);
				pstmt.setInt(4, orderNo);
				pstmt.setInt(5, depth);
				pstmt.setLong(6, vo.getUserNo());
				pstmt.executeQuery();

			}
		} catch (SQLException e) {
			System.out.println("error: " + e);;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (selectpstmt != null) {
					selectpstmt.close();
				}
				if (updatepstmt != null) {
					updatepstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void update(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "update board"
					    + " set title = ?, contents = ?"
					    + " where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());
	
			pstmt.executeQuery();
			
		} catch (SQLException e) {
			System.out.println("error : "+e);
		} finally {
			try {
				
				if(pstmt != null ) {
					pstmt.close();
				}
				if(conn != null ) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void delete(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql ="delete from board where no =?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
	
			pstmt.executeQuery();
			
		} catch (SQLException e) {
			System.out.println("error : "+e);
		} finally {
			try {
				
				if(pstmt != null ) {
					pstmt.close();
				}
				if(conn != null ) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void visitCount(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql ="update board set hit = hit + 1 where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
	
			pstmt.executeQuery();
			
		} catch (SQLException e) {
			System.out.println("error : "+e);
		} finally {
			try {
				
				if(pstmt != null ) {
					pstmt.close();
				}
				if(conn != null ) {
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
