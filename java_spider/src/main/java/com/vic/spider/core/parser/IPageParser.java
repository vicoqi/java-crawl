package com.vic.spider.core.parser;

import java.util.List;

/**
 * vic on 2018/1/30.
 */
public interface IPageParser<T> {
    public List<T> parser(String html);
}
