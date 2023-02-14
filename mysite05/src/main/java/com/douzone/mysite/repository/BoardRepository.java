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
	
	// 게시글 등록
	public void insert(BoardVo vo) {
		sqlSession.insert("board.insert", vo);
	}
	
	// 게시글 리스트
	public List<BoardVo> findAllByPageAndKeyword(String keyword, Integer pageNo, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startOffset", (pageNo - 1) * size);
		map.put("keyword", keyword);
		map.put("size", size);
		
		return sqlSession.selectList("board.findAllByPageAndKeyword", map);
	}
	
	// 게시글 수정
	public void update(BoardVo vo) {
		sqlSession.update("board.update", vo);
	}
	
	// 게시글 삭제
	public void delete(Long no, Long userNo) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("no", no);
		map.put("userNo", userNo);
		sqlSession.delete("board.delete", map);
	}

	// 게시글 총 개수
	public int getTotalCount(String keyword) {
		return 	sqlSession.selectOne("board.totalCount", keyword);
	}

	// 게시글 상세보기
	public BoardVo findByNo(Long no) {
		return sqlSession.selectOne("board.findByNo", no);
	}
	
	// 게시글 수정페이지 상세보기
	public BoardVo findByNoAndUserNo(Long no, Long userNo) {
		Map<String , Long> map = Map.of("no", no, "userNo", userNo);
		return sqlSession.selectOne("board.findByNoAndUserNo", map);
	}
	
	// 댓글 등록할때 groupNo, orderNo 업데이트
	public void updateOrderNo(Long orderNo, Long groupNo) {
		Map<String, Long> map = Map.of("orderNo", orderNo, "groupNo", groupNo);
		sqlSession.update("board.updateOrderNo", map);
	}
	
	// 조회수
	public void visitCount(Long no) {
		sqlSession.update("board.visitCount", no);
	}
}
