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
	
	public void addContents(BoardVo vo) {
		
	}
	
	public BoardVo getContents(Long no) {
		return boardRepository.getContentsNo(no);
	}
	
//	public BoardVo getContents(Long no, Long userNo) {
//		return null;
//		// accsee 제어
//	}	
	
	public void updateContents(BoardVo vo) {
		boardRepository.updateContents(vo);
	}
	
	public void deleteContents(Long no, Long userNo) {
		
	}
	
	public Map<String, Object> getContentsList(int pageNo, String keyword) {
		int totalCount = boardRepository.getTotalCount(keyword);
		
		// 1. view에서 게시판 리스트를 렌더링 하기 위한 데이터 값 계산
		int endPage = (int)(Math.ceil(pageNo/(double)PAGE_SIZE) * PAGE_SIZE);
		int startPage = (endPage - PAGE_SIZE) + 1;
		boolean prePage = startPage == 1 ? false : true;
		boolean nextPage = endPage * LIST_SIZE < totalCount ? true : false;
		int tempEndPage = (int) (Math.ceil(totalCount / (double)LIST_SIZE));
		
		// 2. 리스트 가져오기
		List<BoardVo> list = boardRepository.findAllByPageAndKeyword(pageNo, keyword, LIST_SIZE);

		// 3. 리스트 정보를 맵에 저장
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("startPage", startPage);
		map.put("prePage", prePage);
		map.put("nextPage", nextPage);
		map.put("tempEndPage", tempEndPage);
		map.put("endPage", endPage);
		
		return map;
	}
}
