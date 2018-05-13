package com.vic.spider.core.factory;

import com.vic.spider.core.parser.IPageParser;

/**
 * Created by 单耀 on 2018/1/30.
 */
public class ParserFactory {
    public static IPageParser getParserClass(Class clzz) {
        try {
            return (IPageParser) clzz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }
}
