package com.zacamy.pwmana.util;

import org.apache.log4j.Logger;
import org.springframework.messaging.Message;

/**
 * ͨ����־�����
 * @author Sean
 *
 */
public class Log {

	private static Logger logger;

	private Log() {
	}

	private static Logger getLogger() {
		if (logger == null) {
			logger = Logger.getLogger(Log.class);
		}
		return logger;
	}

	public static void info(String message, Exception e) {
		getLogger().info(message, e);
	}

	
	public static void error(Message message, Exception e) {
		getLogger().error(message.toString(), e);
	}

	
	public static void warn(Message message, Exception e) {
		getLogger().warn(message.toString(), e);
	}
	public static void info(Message message, Exception e) {
		info(message.toString(), e);
	}
	
	
	public static void error(String message, Exception e) {
		getLogger().error(message, e);
	}
	
	
	public static void warn(String message, Exception e) {
		getLogger().warn(message, e);
	}

}
