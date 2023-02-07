package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GalleryVo;

@Repository
public class GalleryRepository {
	@Autowired
	private SqlSession sqlSession;

	public void insert(GalleryVo vo) {
		sqlSession.insert("gallery.insert", vo);
	}
	
	public List<GalleryVo> findAll() {		
		return sqlSession.selectList("gallery.find");
	}
	
	public void delete(Long no) {
		sqlSession.delete("gallery.delete", no);
	}
}
