package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {
	private static final int LIST_SIZE = 5; // 리스팅되는 게시물의 수
	private static final int PAGE_SIZE = 5; // 페이지 리스트의 페이지 수
	
	@Autowired
	private BoardRepository boardRepository;
	
	public void writeContents(BoardVo vo) {
		if(vo.getGroupNo() == null) { // 새 게시물 작성
			Long maxGNo = boardRepository.getMaxGroupNo();
			vo.setGroupNo(maxGNo + 1);
			vo.setOrderNo(1L);
			vo.setDepth(0L);
		} else { // 게시물에 대한 댓글 작성
			vo.setDepth(vo.getDepth() + 1);
			boardRepository.updateOrderNo(vo.getOrderNo(), vo.getGroupNo());
			vo.setOrderNo(vo.getOrderNo() + 1);
		}
		boardRepository.insertContents(vo);
	}
	
	public BoardVo getContents(Long no) {
		return boardRepository.getContentsNo(no);
	}
	
	public BoardVo getContents(Long no, Long userNo) {
		// 보안을 위해 authUser no도 필요
		return boardRepository.getContentsNoandUserNo(no, userNo);
	}
	
	public void visitCount(Long no) {
		boardRepository.visitCount(no);
	}
	
	public void updateContents(BoardVo vo) {
		boardRepository.updateContents(vo);
	}
	
	public void deleteContents(Long no, Long userNo) {
		boardRepository.deleteContents(no, userNo);
	}
	
	public Map<String, Object> getContentsList(int pageNo, String keyword) {
		int totalCount = boardRepository.getTotalCount(keyword);
		
		// 1. view에서 게시판 리스트를 렌더링 하기 위한 데이터 값 계산
		int totalPage = totalCount % LIST_SIZE == 0 ? totalCount/LIST_SIZE : (int)(totalCount/LIST_SIZE) + 1;
		int block = (int)((pageNo -1) / PAGE_SIZE) + 1; // 표시되는 페이지리스트 목록
		int startPage = (block * PAGE_SIZE) - 4; // 시작 페이지
		int endPage = (block * PAGE_SIZE) > totalPage ? totalPage : block * PAGE_SIZE; // 마지막 페이지
		int prePage = pageNo > 1 ? pageNo - 1 : -1; // 이전 페이지 -> pageNo가 1보다 크면 -1씩 아니면 -1을 넘겨줘서 안보이게
		int nextPage = pageNo + 1 > totalPage ? -1 : pageNo + 1; // 다음 페이지 -> pageNo가 totalPage보다 크면 -1을 넘겨줘서 안보이게
		
		// 2. 리스트 가져오기
		List<BoardVo> list = boardRepository.findAllByPageAndKeyword(pageNo, keyword, LIST_SIZE);

		// 3. 리스트 정보를 맵에 저장
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("startPage", startPage);
		map.put("endPage", endPage);
		map.put("prePage", prePage);
		map.put("nextPage", nextPage);
		map.put("keyword", keyword);
		
		return map;
	}
}
