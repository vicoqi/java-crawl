package com.vic.spider.douban.task;

import com.vic.spider.douban.DoubanHttpClient;
import com.vic.spider.proxytool.ProxyPool;
import com.vic.spider.core.entity.Page;
import com.vic.spider.proxytool.entity.Proxy;
import com.vic.spider.core.http.util.HttpClientUtil;
import com.vic.spider.core.util.ProxyUtil;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * vic on 2018/2/6.
 * TODO
 */
public abstract class AbstractTask implements Runnable {//TODO 改成泛型，这样打印日志会更佳明显有助排查错误
    private static Logger logger = LoggerFactory.getLogger(AbstractTask.class);
    protected static DoubanHttpClient doubanHttpClient = DoubanHttpClient.getInstance();
    protected boolean isUseProxy;
    protected String url;
    protected Proxy currentProxy;
    protected int retryTimes;

    public void getPage(String url) {
        this.getPage(url, isUseProxy);
    }

    public void getPage(String url, boolean isUseProxy) {
        this.url = url;
        this.isUseProxy = isUseProxy;

        HttpGet request = new HttpGet(url);
        try {
            Page page = null;
            if (isUseProxy) {
                currentProxy = ProxyPool.proxyQueue.take();
                HttpHost proxy = new HttpHost(currentProxy.getIp(), currentProxy.getPort());
                request.setConfig(HttpClientUtil.getRequestConfigBuilder().setProxy(proxy).build());
                page = doubanHttpClient.getPage(request);
            } else {
                page = doubanHttpClient.getPage(url);
            }
            if (page != null && page.getStatusCode() == 200) {
                if (currentProxy != null)
                    currentProxy.setSuccessfulTimes(currentProxy.getSuccessfulTimes() + 1);
                handle(page);
//                return page;
            } else {
                currentProxy.setFailureTimes(currentProxy.getFailureTimes() + 1);
                retry();
            }
        } catch (Exception e) {
            currentProxy.setFailureTimes(currentProxy.getFailureTimes() + 1);
//            e.printStackTrace();
//            logger.error(e.getMessage(), e);
            retry();
        } finally {
            if (request != null) {
                request.releaseConnection();
            }

            if (currentProxy != null && !ProxyUtil.isDiscardProxy(currentProxy)) {
                ProxyPool.proxyQueue.add(currentProxy);
            } else {
                if (currentProxy != null)
                    logger.info("丢弃代理：" + currentProxy.getProxyStr());
            }
        }
//        return null;
    }

    public abstract void retry();

    public abstract void handle(Page page);

}
