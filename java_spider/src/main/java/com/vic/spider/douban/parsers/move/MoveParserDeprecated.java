package com.vic.spider.douban.parsers.move;

import com.vic.spider.douban.entity.move.Move;
import com.vic.spider.core.parser.IPageParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * vic on 2018/1/30.
 */
@Deprecated
public class MoveParserDeprecated implements IPageParser<Move> {
    public  List<Move> parser(String html) {
        List<Move> list = new ArrayList<Move>();
//        System.out.println(html);
        Move move = new Move();
        try {
            Document document = Jsoup.parse(html);
            Elements elements = document.select("div#info");
            //导演
            Elements directs = elements.select("[rel=v:directedBy]");
            String _direct = move.getDirector();
            for (Element element : directs) {
                if (_direct != null) {
                    _direct += "/" + element.text();
                } else {
                    _direct = element.text();
                }
            }
            move.setDirector(_direct);
            //编剧
            Elements eAttrs = elements.select("span.attrs");
            Elements screenWriters = eAttrs.get(1).select("a");
            String _screenW = move.getScreenwriter();
            for (Element element : screenWriters) {
                if (_screenW != null) {
                    _screenW += "/" + element.text();
                } else {
                    _screenW = element.text();
                }
            }
            //z主演
            Elements mainaActors = elements.select("[rel=v:starring]");
            String _actors = move.getMainaactors();
            for (Element element : mainaActors) {
                if (_actors != null) {
                    _actors += "/" + element.text();
                } else {
                    _actors = element.text();
                }
            }
            move.setMainaactors(_actors);
            //z类型
            Elements types = elements.select("[property=v:genre]");
            String _type = move.getType();
            for (Element element : types) {
                if (_type != null) {
                    _type += "/" + element.text();
                } else {
                    _type = element.text();
                }
            }
            move.setType(_type);

            Elements elementsPL = elements.select("span.pl");

            //制片国家、地区 TODO
            //语言 TODO

            //上映日期
            Elements showDates = elements.select("[property=v:initialReleaseDate]");
            String _showdate = move.getMainaactors();
            for (Element element : showDates) {
                if (_showdate != null) {
                    _showdate += "/" + element.text();
                } else {
                    _showdate = element.text();
                }
            }
            move.setShowdate(_showdate);
            //片长 // TODO: 2018/2/4
            Elements runtime = elements.select("[property=v:runtime]");
            move.setRuntime(runtime.first().text());
            //IMD连接
            Elements imdb = elements.select("[rel=nofollow]");
            move.setImdb(imdb.first().text());

            //评价人数
            Elements rating_sum = document.select("[property=v:votes]");
            move.setVotecount(Integer.valueOf(rating_sum.get(0).text()));
        } catch (Exception e) {

        }

        list.add(move);
        return list;
    }
}
