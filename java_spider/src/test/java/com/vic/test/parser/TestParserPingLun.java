package com.vic.test.parser;


import com.vic.spider.douban.DoubanHttpClient;
import com.vic.spider.douban.task.DouBanPingLunDownLoadTask;
import com.vic.spider.proxytool.ProxyHttpClient;

public class TestParserPingLun {

	public static void main(String[] args) {
//		String URL = "https://movie.douban.com/subject/1292052/comments?status=P";
		String URL = "https://movie.douban.com/subject/1292052/comments?status=P&limit=20&start=";
		DoubanHttpClient doubanHttpClient = DoubanHttpClient.getInstance();
	
		ProxyHttpClient.getInstance().startProxy(false);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //爬取3000条评论
        for(int i=0;i<3000;i+=20){
        	try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        	doubanHttpClient.getDownLoadMoveListExector().execute(new DouBanPingLunDownLoadTask(URL+i,true));
        }
	}

}
