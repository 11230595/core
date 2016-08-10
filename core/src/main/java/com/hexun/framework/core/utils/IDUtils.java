package com.hexun.framework.core.utils;

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
 
    /** The FieldPosition. */
    private static final FieldPosition HELPER_POSITION = new FieldPosition(0);
 
    /** This Format for format the data to special format. */
    private final static Format dateFormat = new SimpleDateFormat("MMddHHmmssS");
 
    /** This Format for format the number to special format. */
    private final static NumberFormat numberFormat = new DecimalFormat("0000");
 
    /** This int is the sequence number ,the default value is 0. */
    private static int seq = 0;
 
    private static final int MAX = 9999;
 
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
		for (int i = 0; i < 50; i++) {
			System.out.println(getId());
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
