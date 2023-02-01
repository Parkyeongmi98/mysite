package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<BoardVo> findAllByPageAndKeyword(int pageNo, String keyword, int size) {
		Map<String, Object> map = new HashMap<>();
		map.put("startOffset", (pageNo-1)*size);
		map.put("keyword", keyword);
		map.put("size", size);
		
		return sqlSession.selectList("board.findAllByPageAndKeyword", map);
	}

	public int getTotalCount(String keyword) {
		sqlSession.selectOne("board.getTotalCount", keyword);
		return 0;
	}

	public BoardVo getContentsNo(Long no) {
		return sqlSession.selectOne("board.getContentsNo", no);
	}
	
	public void updateContents(BoardVo vo) {
		sqlSession.update("board.updateContents", vo);
	}
}
