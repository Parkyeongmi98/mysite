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
		if(vo.getGroupNo() != null) { // 게시물에 대한 댓글 작성 
			boardRepository.updateOrderNo(vo.getOrderNo(), vo.getGroupNo());
		}  
		// 새 게시물 작성
		boardRepository.insert(vo);
	}
	
	public BoardVo getContents(Long no) {
		BoardVo boardvo = boardRepository.findByNo(no);
		
		if(boardvo != null) {
			boardRepository.visitCount(no);
		}
		return boardvo;
	}
	
	public BoardVo getContents(Long no, Long userNo) {
		
		return boardRepository.findByNoAndUserNo(no, userNo);
	}
	
	public void visitCount(Long no) {
		boardRepository.visitCount(no);
	}
	
	public void updateContents(BoardVo vo) {
		boardRepository.update(vo);
	}
	
	public void deleteContents(Long no, Long userNo) {
		boardRepository.delete(no, userNo);
	}
	
	public Map<String, Object> getContentsList(int currentPage, String keyword) {
			
		//1. 페이징을 위한 기본 데이터 계산
		int totalCount = boardRepository.getTotalCount(keyword); 
		int pageCount = (int)Math.ceil((double)totalCount / LIST_SIZE);
		int blockCount = (int)Math.ceil((double)pageCount / PAGE_SIZE);
		int currentBlock = (int)Math.ceil((double)currentPage / PAGE_SIZE);
			
		//2. 파라미터 page 값 검증
		if(currentPage > pageCount) {
			currentPage = pageCount;
			currentBlock = (int)Math.ceil((double)currentPage / PAGE_SIZE);
		}		
			
		if(currentPage < 1) {
			currentPage = 1;
			currentBlock = 1;
		}
			
		//3. view에서 페이지 리스트를 렌더링 하기위한 데이터 값 계산
		int beginPage = currentBlock == 0 ? 1 : (currentBlock - 1) * PAGE_SIZE + 1;
		int prevPage = (currentBlock > 1 ) ? (currentBlock - 1) * PAGE_SIZE : 0;
		int nextPage = (currentBlock < blockCount) ? currentBlock * PAGE_SIZE + 1 : 0;
		int endPage = (nextPage > 0) ? (beginPage - 1) + LIST_SIZE : pageCount;
			
		//4. 리스트 가져오기
		List<BoardVo> list = boardRepository.findAllByPageAndKeyword(keyword, currentPage, LIST_SIZE);
			
		//5. 리스트 정보를 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();
			
		map.put("list", list);
		map.put("totalCount", totalCount);
		map.put("listSize", LIST_SIZE);
		map.put("currentPage", currentPage);
		map.put("beginPage", beginPage);
		map.put("endPage", endPage);
		map.put("prevPage", prevPage);
		map.put("nextPage", nextPage);
		map.put("keyword", keyword);
	
		return map;
	}
	
	
//	public Map<String, Object> getContentsList(int pageNo, String keyword) {
//		int totalCount = boardRepository.getTotalCount(keyword);
//		
//		// 1. view에서 게시판 리스트를 렌더링 하기 위한 데이터 값 계산
//		int totalPage = totalCount % LIST_SIZE == 0 ? totalCount/LIST_SIZE : (int)(totalCount/LIST_SIZE) + 1;
//		int block = (int)((pageNo -1) / PAGE_SIZE) + 1; // 표시되는 페이지리스트 목록
//		int startPage = (block * PAGE_SIZE) - 4; // 시작 페이지
//		int endPage = (block * PAGE_SIZE) > totalPage ? totalPage : block * PAGE_SIZE; // 마지막 페이지
//		int prePage = pageNo > 1 ? pageNo - 1 : -1; // 이전 페이지 -> pageNo가 1보다 크면 -1씩 아니면 -1을 넘겨줘서 안보이게
//		int nextPage = pageNo + 1 > totalPage ? -1 : pageNo + 1; // 다음 페이지 -> pageNo가 totalPage보다 크면 -1을 넘겨줘서 안보이게
//		
//		// 2. 리스트 가져오기
//		List<BoardVo> list = boardRepository.findAllByPageAndKeyword(pageNo, keyword, LIST_SIZE);
//
//		// 3. 리스트 정보를 맵에 저장
//		Map<String, Object> map = new HashMap<>();
//		map.put("list", list);
//		map.put("startPage", startPage);
//		map.put("endPage", endPage);
//		map.put("prePage", prePage);
//		map.put("nextPage", nextPage);
//		map.put("keyword", keyword);
//		
//		return map;
//	}
}
