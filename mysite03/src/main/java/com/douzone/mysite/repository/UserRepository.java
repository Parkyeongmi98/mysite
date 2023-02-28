package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;

@Repository
public class UserRepository {
	@Autowired
	private SqlSession sqlSession; // applicationContext에 설정한 SQL
	
	public void insert(UserVo vo) {
		sqlSession.insert("user.insert", vo);
	}
	
	public UserVo findByEmailandPassword(String email, String password) {
		Map<String, Object> map = new HashMap<>();
		map.put("e", email);
		map.put("p", password);
		
		return sqlSession.selectOne("user.findByEmailandPassword", map); // user.xml에 sql문 불러오기
	}
	
	public UserVo findByNo(Long no) {
		return sqlSession.selectOne("user.findByNo", no);
	}
	
	public UserVo findByEmail(String email) {
		return sqlSession.selectOne("user.findByEmail", email);
	}
	
	public void update(UserVo vo) {
		sqlSession.update("user.update", vo);
	}
}
