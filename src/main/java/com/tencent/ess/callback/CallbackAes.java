package com.tencent.ess.callback;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 回调数据加解密
 */
public class CallbackAes {

    /**
     * 增加Padding
     *
     * @param ciphertext 密文
     * @param blockSize  块大小
     * @return 密文
     */
    public static byte[] pkcs7Padding(byte[] ciphertext, int blockSize) {
        int padding = blockSize - ciphertext.length % blockSize;
        byte[] padtext = repeat((byte) padding, padding);
        ciphertext = append(ciphertext, padtext);
        return ciphertext;
    }

    /**
     * 填充数据
     *
     * @param val   填充数据
     * @param count 数据个数
     * @return 数组
     */
    public static byte[] repeat(byte val, int count) {
        byte[] result = new byte[count];
        for (int i = 0; i < count; i++) {
            result[i] = val;
        }
        return result;
    }

    /**
     * 数组合并
     *
     * @param a 数组a
     * @param b 数组b
     * @return 新数组
     */
    public static byte[] append(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    /**
     * 去掉Padding
     *
     * @param origData 解密后数据
     * @return 解密后数据
     */
    public static byte[] pkcs7UnPadding(byte[] origData) {
        int length = origData.length;
        int unpadding = origData[length - 1];
        byte[] result = new byte[length - unpadding];
        System.arraycopy(origData, 0, result, 0, result.length);
        return result;
    }

    /**
     * 加密数据
     *
     * @param origData 待加密的数据
     * @param key      秘钥
     * @return 密文数据
     */
    public static byte[] aesEncrypt(byte[] origData, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        int blockSize = cipher.getBlockSize();
        origData = pkcs7Padding(origData, blockSize);
        SecretKeySpec keyspec = new SecretKeySpec(key, "AES");
        byte[] iv = new byte[blockSize];
        System.arraycopy(key, 0, iv, 0, iv.length);
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
        byte[] encrypted = cipher.doFinal(origData);
        return Base64.getEncoder().encode(encrypted);
    }

    /**
     * 解密数据
     *
     * @param crypted 密文
     * @param key     秘钥
     * @return 解密后数据
     */
    public static byte[] aesDecrypt(byte[] crypted, byte[] key) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(crypted);
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        int blockSize = cipher.getBlockSize();
        SecretKeySpec keyspec = new SecretKeySpec(key, "AES");
        byte[] iv = new byte[blockSize];
        System.arraycopy(key, 0, iv, 0, iv.length);
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
        byte[] origData = cipher.doFinal(decoded);
        return pkcs7UnPadding(origData);
    }

    /**
     * 测试
     * 注：如果解密时报错 Illegal key size
     * 1、可尝试将jdk升级到到1.8.0_162及以上版本之上
     *
     * 2、如果jdk版本为1.8.0_151 或 1.8.0_152，可加入以下代码开启支持：
     * Security.setProperty("crypto.policy", "unlimited");
     *
     * 3、下载以下jar包置于${java.home}/jre/lib/security/
     * jdk1.6:
     * http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html
     * jdk1.7:
     * http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html
     * jdk1.8:
     * http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
     */
    public static void main(String[] args) throws Exception {
        // 传入CallbackUrlKey
        byte[] key = "***************".getBytes();
        // 加密的数据，base64数据格式
        String cryptedData = "****************";
        // 传入CallbackUrlKey和密文
        byte[] origData = aesDecrypt(cryptedData.getBytes(StandardCharsets.UTF_8), key);
        // 打印解密后的内容，格式为json
        System.out.println(new String(origData, StandardCharsets.UTF_8));
    }
}
