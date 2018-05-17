package com.vic.spider.douban.parsers.move;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.vic.spider.core.constants.ProxyConstants;
import com.vic.spider.core.parser.IPageParser;
import com.vic.spider.core.util.SaveToFileUtil;
import com.vic.spider.douban.entity.move.Move;

public class MoveParserPingLun implements IPageParser<Move> {
	//使线程与文件与对象在一起，所以才是类属性，这样这个保存到文件的工具才可以被多个文件多个线程使用
	private static final SaveToFileUtil SF = new SaveToFileUtil(ProxyConstants.FILE_PATH + "/" + "aa2.txt");

	@Override
	public List<Move> parser(String html) {
		StringBuffer sb = new StringBuffer();
		Document document = Jsoup.parse(html);
	    Elements elements = document.select("div#comments");
	    Elements comments = elements.select("div.comment-item");
	    for(Element element:comments){
	        String commentStr = element.select("p").first().text();
	        sb.append((commentStr+"\r\n"));
//	        fos.write((commentStr+"\r\n").getBytes("UTF-8")); 
	    }
	    SF.saveString2File(sb.toString());
		return null;
	}

}
