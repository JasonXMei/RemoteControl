package com.jason.common.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.slf4j.Logger;

public class LoggerUtil {
	
	public static void printErrorLog(Logger logger, Exception e){
		ByteArrayOutputStream baos = null;
		PrintStream ps = null;
		try {
			baos = new ByteArrayOutputStream();
			ps = new PrintStream(baos);
			e.printStackTrace(ps);
			String exceptionStack = baos.toString();    
			logger.error(exceptionStack);
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			try {
				if(baos != null) baos.close();
				if(ps != null) ps.close();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	} 
}
