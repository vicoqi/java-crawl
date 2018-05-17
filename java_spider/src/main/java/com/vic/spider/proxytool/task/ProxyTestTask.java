package com.vic.spider.proxytool.task;

import com.vic.spider.proxytool.ProxyHttpClient;
import com.vic.spider.proxytool.ProxyPool;
import com.vic.spider.core.entity.Page;
import com.vic.spider.proxytool.entity.Proxy;
import com.vic.spider.core.http.util.HttpClientUtil;
import com.vic.spider.core.constants.ProxyConstants;
import com.vic.spider.core.util.ProxyUtil;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by 单耀 on 2018/1/28.
 * 测试代理任务
 * 测试下载的代理是否可用
 */
public class ProxyTestTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(ProxyTestTask.class);
    private Proxy proxy;

    public ProxyTestTask(Proxy proxy) {
        this.proxy = proxy;
    }

    public void run() {
        HttpGet request = new HttpGet(ProxyConstants.PROXYTEST_URL);
        try {
            HttpHost proxyHost = new HttpHost(proxy.getIp(), proxy.getPort());
            RequestConfig requestConfig = HttpClientUtil.getRequestConfigBuilder().setProxy(proxyHost).build();
            request.setConfig(requestConfig);
            Page page = ProxyHttpClient.getInstance().getPage(request);
            String logStr = Thread.currentThread().getName() + " " + proxy.getProxyStr() +
                    "  executing request " + page.getUrl()  + " response statusCode:" + page.getStatusCode();

            if (page == null || page.getStatusCode() != 200) {
//                logger.warn("该代理不可用：" + logStr);
                return;
            }
            if (page.getStatusCode() == 200) {
//                logger.debug(proxy.getProxyStr() + "-----代理可用-----");
//                logger.debug(proxy.toString() + "--------" + page.toString());
                ProxyPool.lock.writeLock().lock();
                logger.warn("*-*-*-*-*前可用队列代理数量:"+ProxyPool.proxyQueue.size());
                logger.warn("-*-*-*-*-前可用set集合代理数量:"+ProxyPool.proxySet.size());
                if(ProxyPool.proxySet.add(proxy)){
                	ProxyPool.proxyQueue.add(proxy);
                	logger.warn("*-*-*-*-*目入可用队列代理数量:"+ProxyPool.proxyQueue.size());
                    logger.warn("-*-*-*-*-目入可用set集合代理数量:"+ProxyPool.proxySet.size());
                }
                ProxyPool.lock.writeLock().unlock();
                logger.warn("*-*-*-*-*目后可用队列代理数量:"+ProxyPool.proxyQueue.size());
                logger.warn("-*-*-*-*-目后可用set集合代理数量:"+ProxyPool.proxySet.size());
                
            }
        } catch (IOException e) {
//            logger.debug("IOException", e);
        } finally {
            if (request != null) {
                request.releaseConnection();
            }

//            if (proxy != null && !ProxyUtil.isDiscardProxy(proxy)){
//                ProxyPool.proxyQueue.add(proxy);
//            }
        }
    }
}
