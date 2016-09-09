package com.hexun.framework.core.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ID及随机数处理类
 * @author zhoudong
 *
 */
public class IDUtils {
	/** .log */
    private static final Logger logger = LoggerFactory.getLogger(IDUtils.class);
 
    /** 偏移位置. */
    private static final FieldPosition HELPER_POSITION = new FieldPosition(DateFormat.DATE_FIELD);
 
    /** 日期格式. */
    private final static Format dateFormat = new SimpleDateFormat("yyMMddHHmmssS");
 
    /** 数字格式，在日期后做俩位的自增. */
    private final static NumberFormat numberFormat = new DecimalFormat("00");
 
    /** 序列开始位. */
    private static int seq = 0;
    
    /** 序列最大位 */
    private static final int MAX = 99;
 
    /**
     * 时间格式生成序列
     * @return String
     */
    public static synchronized String generateSequenceNo() {
 
        Calendar rightNow = Calendar.getInstance();
 
        StringBuffer sb = new StringBuffer();
 
        dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);
        numberFormat.format(seq, sb, HELPER_POSITION);
 
        if (seq == MAX) {
            seq = 0;
        } else {
            seq++;
        }
 
        logger.info("THE SQUENCE IS :" + sb.toString());
 
        return sb.toString();
    }

	/**
	 * 生成ID
	 * @author zhoudong
	 * @return
	 */
	public static String getId() {
		return generateSequenceNo();
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 200; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(getId());
				}
			}).start();
			
		}
	}
	
	/**
	 * 生成随机数
	 * 
	 * @return
	 */
	public static int getRandom() {
		return (int) (Math.random() * 1000);
	}
	
}
