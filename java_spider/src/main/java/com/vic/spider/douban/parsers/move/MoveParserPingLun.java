package com.vic.spider.douban.parsers.move;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.vic.spider.core.constants.ProxyConstants;
import com.vic.spider.core.parser.IPageParser;
import com.vic.spider.douban.entity.move.Move;

public class MoveParserPingLun implements IPageParser<Move> {

	@Override
	public List<Move> parser(String html) {
		String path = ProxyConstants.FILE_PATH + "/" + "aa.txt";
		FileOutputStream fos = null;
		try {
			//true表示在文件末尾追加  
			fos = new FileOutputStream(path,true);
			Document document = Jsoup.parse(html);
	        Elements elements = document.select("div#comments");
	        Elements comments = elements.select("div.comment-item");
	        for(Element element:comments){
	        	String commentStr = element.select("p").first().text();
	        	fos.write((commentStr+"\r\n").getBytes("UTF-8")); 
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
        
		return null;
	}

}
