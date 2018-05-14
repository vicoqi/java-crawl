package com.vic.spider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vic.spider.common.config.CommonConfig;
import com.vic.spider.douban.DoubanHttpClient;
import com.vic.spider.proxytool.ProxyHttpClient;



public class Start {
	private static Logger logger = LoggerFactory.getLogger(Start.class);
	public static void main(String[] args) {
	        ProxyHttpClient.getInstance().startProxy();
	        try {
	            Thread.sleep(10000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        if (CommonConfig.FUTURE_DOUBAN) {
	            DoubanHttpClient.getInstance().startDouBan();
	        }

//	        if (CommonConfig.FUTURE_ZHIHU) {
//	            ZhiHuHttpClient.getInstance().startZhiHu();
//	        }
	    }
}
