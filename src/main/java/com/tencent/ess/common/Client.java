package com.tencent.ess.common;

import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ess.v20201111.EssClient;


/**
 * 客户端调用实例
 */
public class Client {

    // 构造默认Api客户端调用实例
    public static EssClient getEssClient() {
        return getEssClient(Config.SecretId, Config.SecretKey, Config.EndPoint);
    }

    // 构造默认File客户端调用实例
    public static EssClient getEssFileClient() {
        return getEssClient(Config.SecretId, Config.SecretKey, Config.FileServiceEndPoint);
    }

    /**
     * 构造Ess客户端调用实例
     *
     * @param secretId  腾讯云的密钥对secretId
     * @param secretKey 腾讯云的密钥对secretKey
     * @param endPoint  腾讯云的服务域名
     * @return Ess客户端调用实例
     */
    public static EssClient getEssClient(String secretId, String secretKey, String endPoint) {
        // 实例化一个证书对象，入参需要传入腾讯云账户secretId，secretKey
        Credential Credential = new Credential(secretId, secretKey);
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = getClientProfile(endPoint);
        String guangzhouRegion = "ap-guangzhou";
        EssClient client = new EssClient(Credential, guangzhouRegion, clientProfile);
        return client;
    }

    /**
     * 构造Ess客户端配置
     *
     * @param endPoint 腾讯云的服务域名
     * @return Ess客户端配
     */
    private static ClientProfile getClientProfile(String endPoint) {
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = getHttpProfile(endPoint);
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod("TC3-HMAC-SHA256");  // 指定签名算法(默认为HmacSHA256)
        clientProfile.setHttpProfile(httpProfile);

        return clientProfile;
    }

    /**
     * 构造http配置信息
     *
     * @param endPoint 腾讯云的服务域名
     * @return http配置信息
     */
    private static HttpProfile getHttpProfile(String endPoint) {
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setConnTimeout(30); // 请求超时时间，单位为秒(默认60秒)
        // httpProfile.setReadTimeout(15);
        // httpProfile.setWriteTimeout(15);
        httpProfile.setReqMethod("POST");  // post请求(默认为post请求)
        httpProfile.setEndpoint(endPoint);// 指定接入地域域名(默认就近接入)
        return httpProfile;
    }
}
