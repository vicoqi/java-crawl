package com.vic.spider.douban.task;

import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vic.spider.core.entity.Page;
import com.vic.spider.core.factory.ParserFactory;
import com.vic.spider.core.http.util.HttpClientUtil;
import com.vic.spider.core.parser.IPageParser;
import com.vic.spider.core.util.ProxyUtil;
import com.vic.spider.douban.DoubanHttpClient;
import com.vic.spider.douban.entity.move.Move;
import com.vic.spider.douban.parsers.move.MoveDetailInfoParser;
import com.vic.spider.douban.parsers.move.MoveParserPingLun;
import com.vic.spider.proxytool.ProxyPool;
import com.vic.spider.proxytool.entity.Proxy;

public class DouBanPingLunDownLoadTask implements Runnable{
	private static Logger logger = LoggerFactory.getLogger(DouBanPingLunDownLoadTask.class);
	private static DoubanHttpClient doubanHttpClient = DoubanHttpClient.getInstance();
	private String url;
    private boolean enableProxy;
    private Proxy proxy;
    private int DEFAULT_MAX_RETRYS = 3;//默认重试的最大次数
    public DouBanPingLunDownLoadTask(String url, boolean enableProxy) {
        this.url = url;
        this.enableProxy = enableProxy;
    }
    public DouBanPingLunDownLoadTask(String url, boolean enableProxy, int retryTime) {
        this.url = url;
        this.enableProxy = enableProxy;
        this.DEFAULT_MAX_RETRYS = retryTime;
    }
	@Override
	public void run() {
		Page pageResp = null;
		int count = 0;
		do{
			try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
			pageResp = getPageFromUrl(enableProxy,url);
			count++;
		}while(!(pageResp != null && pageResp.getStatusCode() == 200));
		logger.info("这个url重试了多少次："+count);
		IPageParser parser = ParserFactory.getParserClass(MoveParserPingLun.class);
        parser.parser(pageResp.getHtml());
		
	}
	
	private Page getPageFromUrl(boolean enableProxy,String urlStr){
		Page page = null;
		HttpGet request = new HttpGet(urlStr);
		try{
			if (enableProxy) {
	            proxy = ProxyPool.proxyQueue.take();
	            HttpHost host =new HttpHost(this.proxy.getIp(), this.proxy.getPort());
	            request.setConfig(HttpClientUtil.getRequestConfigBuilder().setProxy(host).build());
	            page = doubanHttpClient.getPage(request);
	        } else {
	           page = doubanHttpClient.getPage(url);
	        }
		}catch(Exception e){
			logger.error("getPageFromUrl方法中 的队列长度："+ProxyPool.proxyQueue.size());
			proxy.setFailureTimes(proxy.getFailureTimes() + 1);
		}finally{
				if (request != null) {
	                request.releaseConnection();
	            }
				if (proxy != null && !ProxyUtil.isDiscardProxy(proxy)){
	                ProxyPool.proxyQueue.add(proxy);
	            } else {
	                logger.info("丢弃代理：" + proxy.getProxyStr());
	            }
		}
		return page;
	}
	
}
