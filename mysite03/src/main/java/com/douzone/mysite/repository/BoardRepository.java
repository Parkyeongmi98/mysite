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
		return 	sqlSession.selectOne("board.getTotalCount", keyword);
	}

	// 게시글 상세보기
	public BoardVo getContentsNo(Long no) {
		return sqlSession.selectOne("board.getContentsNo", no);
	}
	
	// 게시글 수정페이지 상세보기
	public BoardVo getContentsNoandUserNo(Long no, Long userNo) {
		Map<String , Object> map = Map.of("no", no, "userNo", userNo);
		return sqlSession.selectOne("board.getContentsNoandUserNo", map);
	}
	
	// 게시글 수정
	public void updateContents(BoardVo vo) {
		sqlSession.update("board.updateContents", vo);
	}
	
	// 게시글 삭제
	public void deleteContents(Long no, Long userNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("userNo", userNo);
		sqlSession.delete("board.deleteContents", map);
	}
	
	// 게시글 등록
	public void insertContents(BoardVo vo) {
		sqlSession.insert("board.insertContents", vo);
	}
	
	// max groupNo 찾기
	public Long getMaxGroupNo() {
		return sqlSession.selectOne("board.findMaxNo");
	}
	
	// 댓글 등록할때 groupNo, orderNo 업데이트
	public void updateOrderNo(Long orderNo, Long groupNo) {
		Map<String, Long> map = Map.of("orderNo", orderNo, "groupNo", groupNo);
		sqlSession.update("board.updateGroupNo", map);
	}
	
	// 조회수
	public void visitCount(Long no) {
		sqlSession.update("board.visitCount", no);
	}
}
