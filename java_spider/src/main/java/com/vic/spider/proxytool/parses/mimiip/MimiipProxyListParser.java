package com.vic.spider.proxytool.parses.mimiip;


import com.vic.spider.core.parser.IPageParser;
import com.vic.spider.proxytool.entity.Proxy;
import com.vic.spider.core.constants.ProxyConstants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 单耀 on 2017/12/5.
 */
public class MimiipProxyListParser implements IPageParser<Proxy> {
    public List<Proxy> parser(String hmtl) {
        Document document = Jsoup.parse(hmtl);
        Elements elements = document.select("table[class=list] tr");
        List<Proxy> proxyList = new ArrayList<Proxy>(elements.size());
        for (int i = 1; i < elements.size(); i++){
            String ip = elements.get(i).select("td:eq(0)").first().text();
            String port  = elements.get(i).select("td:eq(1)").first().text();
            proxyList.add(new Proxy(ip, Integer.valueOf(port), ProxyConstants.TIME_INTERVAL, "mmiip"));
        }
        return proxyList;
    }
}
