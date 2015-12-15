package com.yicheng6.sso.auth.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AuthIdManager {

	// 创建提供生成随机数的对象的队列，提供以同步方式保证线程安全，当队列中没有可用对象创建后放入。
	private final Queue<SecureRandom> randoms = new ConcurrentLinkedQueue<SecureRandom>();

	// 参考Tomcat生成JSessionId方式随机生成一个Id，此处不保证唯一性，调用时加以控制。
	public String generateAuthId(int authIdLength) {

		byte[] random = new byte[16];

		StringBuilder buffer = new StringBuilder();

		int resultLenBytes = 0;

		// 多次获取标准，拼接为指定长度字符串
		while (resultLenBytes < authIdLength) {
			// 获取标准16字节随机数组
			getRandomBytes(random);

			// 转换16字节内字符串
			for (int j = 0; j < random.length && resultLenBytes < authIdLength; j++) {
				// 使用位运算获取字节前、后4位
				byte b1 = (byte) ((random[j] & 0xf0) >> 4);
				byte b2 = (byte) (random[j] & 0x0f);
				// 将随机字节组转换拼接为16进制字符串
				if (b1 < 10)
					buffer.append((char) ('0' + b1));
				else
					buffer.append((char) ('A' + (b1 - 10)));
				if (b2 < 10)
					buffer.append((char) ('0' + b2));
				else
					buffer.append((char) ('A' + (b2 - 10)));
				resultLenBytes++;
			}
		}

		return buffer.toString();
	}

	private void getRandomBytes(byte[] bytes) {
		SecureRandom random = randoms.poll();
		if (random == null)
			random = createSecureRandom();
		
		random.nextBytes(bytes);
		randoms.add(random);
	}

	// 创建一个随机数提供给AuthId
	private SecureRandom createSecureRandom() {
		SecureRandom result = null;

		if (result == null) {
			try {
				result = SecureRandom.getInstance("SHA1PRNG");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}

		if (result == null) {
			result = new SecureRandom();
		}
		
		//seed
		result.nextInt();

		return result;
	}
	
	public static void main(String[] args) {
		AuthIdManager authIdManager = new AuthIdManager();
		System.out.println(authIdManager.generateAuthId(10));
	}
}
