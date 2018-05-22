package com.vic.spider.douban.parsers.move;

import com.vic.spider.douban.entity.move.MoveList;
import com.vic.spider.core.parser.IPageParser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * vic on 2018/2/4.
 */
@Deprecated
public class MoveListParser implements IPageParser<MoveList> {
    private static Logger logger = LoggerFactory.getLogger(MoveListParser.class);
    public List<MoveList> parser(String html) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(html);
            JSONArray jsonArray = jsonObject.getJSONArray("subjects");
            List<MoveList> move1s = (List<MoveList>) JSONArray.toCollection(jsonArray, MoveList.class);
            return move1s;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
