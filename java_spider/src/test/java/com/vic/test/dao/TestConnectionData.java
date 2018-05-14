package com.vic.test.dao;

import org.apache.ibatis.session.SqlSession;

import com.vic.spider.core.util.MyBatiesUtils;

public class TestConnectionData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SqlSession session = MyBatiesUtils.getSqlSession(true);
	}

}
