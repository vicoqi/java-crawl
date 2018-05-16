package com.vic.test.parser;

import com.vic.spider.douban.DoubanHttpClient;
import com.vic.spider.douban.parsers.move.MoveParserPingLun;
import com.vic.spider.douban.task.DouBanPingLunDownLoadTask;
import com.vic.spider.douban.task.move.SpiderWithTypeTask;
import com.vic.spider.proxytool.ProxyHttpClient;

public class TestParserPingLun {

	public static void main(String[] args) {
//		String URL = "https://movie.douban.com/subject/1292052/comments?status=P";
		String URL = "https://movie.douban.com/subject/1292052/comments?status=P&limit=20&start=";
		DoubanHttpClient doubanHttpClient = DoubanHttpClient.getInstance();
	
		ProxyHttpClient.getInstance().startProxy();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//		new Thread(new DouBanPingLunDownLoadTask(URL,true)).start();
        for(int i=0;i<100;i+=20){
        	doubanHttpClient.getDownLoadMoveListExector().execute(new DouBanPingLunDownLoadTask(URL+i,true));
        }

	}

}
