package cn.dreamdeck.utils;

public class Util {
	// 将16进制的字符串转换成字节数组

	public static byte[] hexString2Bytes(String src) {
		int l = src.length() / 2;
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			ret[i] = (byte) Integer
					.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
		}
		return ret;
	}
	public static byte[] hexStrToBinaryStr(String hexString) {
		hexString = hexString.replaceAll(" ", "");
		int len = hexString.length();
		int index = 0;

		byte[] bytes = new byte[len / 2];
		while (index < len) {
			String sub = hexString.substring(index, index + 2);
			bytes[index/2] = (byte)Integer.parseInt(sub,16);
			index += 2;
		}
		return bytes;
	}
	public static byte hexToByte(String inHex){
		return (byte)Integer.parseInt(inHex,16);
	}
	public static int getUnsignedByte (byte data){
		return data&0xff;
	}

	public static byte[] hexStrToBinaryStr4(String hexString) {
		hexString = hexString.replaceAll(" ", "");
		int len = hexString.length();
		int index = 0;

		byte[] bytes = new byte[len / 4];
		while (index < len) {
			String sub = hexString.substring(index, index + 4);
			bytes[index/4] = (byte)Integer.parseInt(sub,16);
			index += 4;
		}
		return bytes;
	}

	/**
	 * 字节数组转换为十六进制字符串
	 *
	 * @param b
	 *            byte[] 需要转换的字节数组
	 * @return String 十六进制字符串
	 */
	public static final String byte2hex(byte b[]) {
		if (b == null) {
			throw new IllegalArgumentException("Argument b ( byte array ) is null! ");
		}
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xff);
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	// 将字节数组转换为16进制字符串
	public static String BinaryToHexStrings(byte[] bytes) {
		String hexStr = "0123456789ABCDEF";
		String result = "";
		String hex = "";
		for (byte b : bytes) {
			hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
			hex += String.valueOf(hexStr.charAt(b & 0x0F));
			result += hex ;
		}
		return result;
	}

	// 将字节数组转换为16进制字符串
	public static String BinaryToHexString(byte[] bytes) {
		String hexStr = "0123456789ABCDEF";
		String result = "";
		String hex = "";
		for (byte b : bytes) {
			hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
			hex += String.valueOf(hexStr.charAt(b & 0x0F));
			result += hex + " ";
		}
		return result;
	}
}
