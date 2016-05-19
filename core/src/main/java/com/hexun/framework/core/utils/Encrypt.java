package com.hexun.framework.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * MD5加密
 * @author zhoudong
 *
 */
public class Encrypt {
	public static final String getMD5(String text) {
		// return text;
		byte[] intext = text.getBytes();
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		byte[] md5rslt = md5.digest(intext);
		StringBuffer verifyMsg = new StringBuffer();
		for (int i = 0; i < md5rslt.length; i++) {
			int hexChar = 0xFF & md5rslt[i];
			String hexString = Integer.toHexString(hexChar);
			hexString = (hexString.length() == 1) ? "0" + hexString : hexString;
			verifyMsg.append(hexString);
		}
		return verifyMsg.toString().toLowerCase();
	}
	
	

	public static byte [] key = {-42, 1, 25, 25, -57, 84, 67, 32};
	
	static byte[] encrypt(byte[] data, byte[] b) throws Exception {
		DESKeySpec dks = new DESKeySpec(b);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		SecureRandom sr = new SecureRandom();
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key, sr);
		byte[] encryptedData = cipher.doFinal(data);
		return encryptedData;
	}

	static byte[] decrypt(byte[] data, byte[] b) throws Exception {
		DESKeySpec dks = new DESKeySpec(b);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		SecureRandom sr = new SecureRandom();
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key, sr);
		byte[] decryptedData = cipher.doFinal(data);
		return decryptedData;
	}

	static byte[] getKey() throws Exception {
		SecureRandom sr = new SecureRandom();
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		kg.init(sr);
		SecretKey key = kg.generateKey();
		byte[] b = key.getEncoded();
		return b;
	}
	
    public static String shortUrl(String url,int random) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "gohome" ;
        // 要使用生成 URL 的字符
        String[] chars = new String[] { "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,
               "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,
               "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" , "4" , "5" ,
               "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,
               "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
               "U" , "V" , "W" , "X" , "Y" , "Z"};

        // 对传入网址进行 MD5 加密
        String sMD5EncryptResult = getMD5(key + url);
        String hex = sMD5EncryptResult;
        String[] resUrl = new String[4];
        for ( int i = 0; i < 4; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong (sTempSubString, 16);
            String outChars = "" ;
            for ( int j = 0; j < 6; j++) {
               // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
               long index = 0x0000003D & lHexLong;
               // 把取得的字符相加
               outChars += chars[( int ) index];
               // 每次循环按位右移 5 位
               lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        return resUrl[random];
     }
//6位短连接
    public static String shortUrl(String url) {
		// 可以自定义生成 MD5 加密字符传前的混合 KEY
		String key = String.valueOf(System.currentTimeMillis());
		// 要使用生成 URL 的字符
		String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h",
				"i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
				"u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
				"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
				"U", "V", "W", "X", "Y", "Z"

		};
		// 对传入网址进行 MD5 加密
		String sMD5EncryptResult = getMD5(key + url);
		String hex = sMD5EncryptResult;
		String resUrl = "";
		// 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
		String sTempSubString = hex.substring(0 * 8, 0 * 8 + 8);
		// 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用
		// long ，则会越界
		long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
		String outChars = "";
		for (int j = 0; j < 6; j++) {
			// 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
			long index = 0x0000003D & lHexLong;
			// 把取得的字符相加
			outChars += chars[(int) index];
			// 每次循环按位右移 5 位
			lHexLong = lHexLong >> 5;
		}
		// 把字符串存入对应索引的输出数组
		resUrl = outChars;
		return resUrl;
	}
//	   public static void main(String[] args) throws Exception {
//	    	  byte[] b = key;//Encrypt.getKey();
//	    	  System.out.println("Length:" + b.length);
//	    	  for(byte temp : b) {
//	    	   System.out.print(temp + " ");
//	    	  }
//	    	  System.out.println("");
//	    	  
//	    	  String s = "I love you.";
//	    	  System.out.println(s);
//	    	  byte[] encryptedData = Encrypt.encrypt(s.getBytes(), b);
//	    	  System.out.println(new String(encryptedData));
//	    	  byte[] decryptedData = Encrypt.decrypt(encryptedData, b);
//	    	  System.out.println(new String(decryptedData));
//	    	 }	
	public static void main(String[] args) throws Exception {
//		System.out.println(getMD5("admin"));
//		System.out.println(getMD5(getMD5("admin")));
	       String sLongUrl = "http://tech.sina.com.cn/?3BD768E58042156E54626860E241E999" ; // 3BD768E58042156E54626860E241E999
//	       String[] aResult = shortUrl (sLongUrl,0);
//	       // 打印出结果
//	       for ( int i = 0; i < aResult. length ; i++) {
//	           System. out .println( "[" + i + "]:::" + aResult[i]);
//	       }

		
	}
	
	
	
	/**
	 * 方法描述: md5签名
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static String md5Digest(String src) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] b = md.digest(src.getBytes("UTF-8"));

		return byte2HexStr(b);
	}

	/**
	 * 字节数组转化为大写16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2HexStr(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			String s = Integer.toHexString(b[i] & 0xFF);
			if (s.length() == 1) {
				sb.append("0");
			}

			sb.append(s.toUpperCase());
		}

		return sb.toString();
	}

}
