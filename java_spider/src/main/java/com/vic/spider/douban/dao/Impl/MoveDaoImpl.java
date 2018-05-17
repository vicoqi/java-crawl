package com.vic.spider.douban.dao.Impl;

import com.vic.spider.common.dao.Impl.BaseDaoImpl;
import com.vic.spider.core.util.MyBatiesUtils;
import com.vic.spider.douban.dao.IMoveDao;
import com.vic.spider.douban.entity.move.Move;
import com.vic.spider.douban.mapper.MoveMapper;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by vic on 2018/3/10.
 */
public class MoveDaoImpl extends BaseDaoImpl<Move> implements IMoveDao {

    private static Logger logger = LoggerFactory.getLogger(MoveDaoImpl.class);

    public void insert(Move move) {
        super.insert(move);
    }

    public void inserSelective(Move move) {
        SqlSession session = MyBatiesUtils.getSqlSession(true);
        try {
            MoveMapper moveMapper = session.getMapper(MoveMapper.class);
            Move m = moveMapper.selectByPrimaryKey(move.getId());
            if (m == null) {
                moveMapper.insertSelective(move);
                session.commit();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            session.rollback();
        } finally {
            //这里并不是断开连接而是将连接池还给连接池，解除占用
            session.close();
        }
    }

    public void insertList(List<Move> moveList) {
        for (Move move : moveList) {
//            inserSelective(move);
        	logger.debug("**-*-*-*-*-*-*\r\n"+move.toString());
        }
    }

    public Move selectByPrimaryKey(String id) {
        SqlSession session = MyBatiesUtils.getSqlSession(true);
        MoveMapper moveMapper = session.getMapper(MoveMapper.class);
        return moveMapper.selectByPrimaryKey(id);
    }

    public void update(Move move) {
        SqlSession session = MyBatiesUtils.getSqlSession(true);
        try {
            MoveMapper moveMapper = session.getMapper(MoveMapper.class);
//            moveMapper.updateByPrimaryKey(move);
//            session.commit();
            logger.debug(move.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            session.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
