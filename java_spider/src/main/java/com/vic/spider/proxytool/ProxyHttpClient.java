package com.vic.spider.proxytool;

import com.vic.spider.proxytool.entity.Proxy;
import com.vic.spider.proxytool.task.ProxyPageTask;
import com.vic.spider.core.constants.ProxyConstants;
import com.vic.spider.core.http.client.AbstractHttpClient;
import com.vic.spider.core.task.ProxySerializeTask;
import com.vic.spider.core.util.MyIOutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;

/**
 * vic on 2018/1/27.
 */
public class ProxyHttpClient extends AbstractHttpClient {
    private static Logger logger = LoggerFactory.getLogger(ProxyHttpClient.class);
    public static volatile boolean isContinue = true;
    private volatile static ProxyHttpClient instance;
    //下载代理页面的线程池
    private ThreadPoolExecutor proxyDoloadThreadExector;

    private ThreadPoolExecutor proxyProxyTestExector;
    public static ProxyHttpClient getInstance() {
        if (instance == null) {
            synchronized (ProxyHttpClient.class) {
                if (instance == null) {
                    instance = new ProxyHttpClient();
                }
            }
        }
        return instance;
    }

    public ProxyHttpClient() {
        initThreadPool();
    }

    //初始化线程池
    private void initThreadPool() {
        //线程通过线程工厂创建，这样每个线程都会有名字，以便于
        proxyDoloadThreadExector = new ThreadPoolExecutor(100, 100, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(100),
                new ThreadFactory() {
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "proxyDoloadThreadExector" + r.hashCode());
                    }
                });

        proxyProxyTestExector = new ThreadPoolExecutor(100, 100, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(100),
                new ThreadFactory() {
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "proxyProxyTestExector" + r.hashCode());
                    }
                });
    }

    public ThreadPoolExecutor getProxyDoloadThreadExector() {
        return proxyDoloadThreadExector;
    }

    public void setProxyDoloadThreadExector(ThreadPoolExecutor proxyDoloadThreadExector) {
        this.proxyDoloadThreadExector = proxyDoloadThreadExector;
    }

    public ThreadPoolExecutor getProxyProxyTestExector() {
        return proxyProxyTestExector;
    }

    public void setProxyProxyTestExector(ThreadPoolExecutor proxyProxyTestExector) {
        this.proxyProxyTestExector = proxyProxyTestExector;
    }

    public void startProxy(boolean isUseProxyFile) {
        //是否爬取新的代理
    	if(!isUseProxyFile){
	        new Thread(new Runnable() {
	            public void run() {
	//                while(isContinue) {
	                for (String url : ProxyPool.proxyMap.keySet()) {
	                    proxyDoloadThreadExector.execute(new ProxyPageTask(url, false));
	                    try {
	                        Thread.sleep(5000);
	                    } catch (InterruptedException e) {
	                        logger.error(e.getMessage(), e);
	                    }
	                }
	                try {
	                    Thread.sleep(1000 * 60 * 60);
	                } catch (InterruptedException e) {
	                    logger.error(e.getMessage(), e);
	                }
	//                }
	            }
	        }).start();
	
	        //序列化代理线程
	        new Thread(new ProxySerializeTask()).start();
    	}else {
    		try {
                List<Proxy> proxyList = (List<Proxy>) MyIOutils.deserializeObject(ProxyConstants.PROXYSER_FILE_NMAE);
                if (proxyList != null) {
                    ProxyPool.proxyQueue.addAll(new DelayQueue<Proxy>(proxyList));
                    logger.info("反序列化后代理数量：" + ProxyPool.proxyQueue.size());
                }
            } catch (Exception e) {
            	
            }
    	}
    }

    /**
     * 停止爬取代理，isNow:true采用shutdonwNow，isNow:false 采用shutdown
     * @param isNow
     */
    public void shutStopSpiderProxy(boolean isNow) {
        if (isNow) {
            this.proxyDoloadThreadExector.shutdownNow();
            this.proxyProxyTestExector.shutdownNow();
        } else {
            this.proxyDoloadThreadExector.shutdown();
            while (proxyDoloadThreadExector.isTerminated()) {
                this.proxyDoloadThreadExector.shutdown();
            }
        }
        this.isContinue = false;
    }

}
