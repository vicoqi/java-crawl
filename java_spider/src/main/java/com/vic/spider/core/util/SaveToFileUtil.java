package com.vic.spider.core.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vic.spider.douban.task.DouBanPingLunDownLoadTask;

public class SaveToFileUtil {
	private static Logger logger = LoggerFactory.getLogger(SaveToFileUtil.class);
	private String path;
	public SaveToFileUtil(String path){
		this.path = path;
	}

	public void saveString2File(String content) {
		FileOutputStream fos = null;
		synchronized (this) {
			//true表示在文件末尾追加
			try{
				fos = new FileOutputStream(this.path,true);
				fos.write(content.getBytes("UTF-8")); 
			}catch (FileNotFoundException e) {
				logger.error("文件没找到");
				e.printStackTrace();
			}catch (UnsupportedEncodingException e){
				logger.error("不支持以UTF-8来读取内容");
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("IO 异常写入失败");
				e.printStackTrace();
			}
			finally{
				if(fos!=null){
					try {
						fos.close();
					} catch (IOException e) {
						logger.error("关闭写入流失败");
						e.printStackTrace();
					}
				}
			}
			
		}
	}
}
