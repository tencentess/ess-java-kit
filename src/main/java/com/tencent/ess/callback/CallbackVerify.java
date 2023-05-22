package com.tencent.ess.callback;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 回调消息验证签名
 */
public class CallbackVerify {


    public static void main(String[] args) {

        try {
            // 回调消息体
            String payload = "**********";
            // secretToken 创建应用号时配置的
            String secretToken = "**********";

            // 1. 取出header [Content-Signature]
            String signFromHeader = "***********";
            // 2. 验证签名
            String hash = "sha256=" + HMACSHA256(payload, secretToken);

            //3. 如果验证通过，继续处理。如果不通过，忽略该请求
            System.out.println(hash.equals(signFromHeader));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

}

