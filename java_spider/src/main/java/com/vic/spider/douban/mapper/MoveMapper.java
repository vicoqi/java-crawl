package com.vic.spider.douban.mapper;


import com.vic.spider.douban.entity.move.Move;

public interface MoveMapper {
    int deleteByPrimaryKey(String id);

    int insert(Move record);

    int insertSelective(Move record);

    Move selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Move record);

    int updateByPrimaryKey(Move record);
}