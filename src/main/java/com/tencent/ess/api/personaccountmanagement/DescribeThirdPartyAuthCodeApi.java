package com.tencent.ess.api.personaccountmanagement;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.DescribeThirdPartyAuthCodeRequest;
import com.tencentcloudapi.ess.v20201111.models.DescribeThirdPartyAuthCodeResponse;

/**
 * 通过AuthCode查询用户是否实名
 *
 * 官网文档：https://cloud.tencent.com/document/product/1323/70368
 */
public class DescribeThirdPartyAuthCodeApi {
    /**
     * 通过AuthCode查询用户是否实名
     *
     * @param authCode 电子签小程序跳转客户小程序时携带的授权查看码
     * @return DescribeThirdPartyAuthCodeResponse
     */
    public static DescribeThirdPartyAuthCodeResponse describeThirdPartyAuthCode(String authCode)
            throws TencentCloudSDKException {

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        DescribeThirdPartyAuthCodeRequest request = new DescribeThirdPartyAuthCodeRequest();
        request.setAuthCode(authCode);

        return client.DescribeThirdPartyAuthCode(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            String authCode = "****************";

            DescribeThirdPartyAuthCodeResponse response = DescribeThirdPartyAuthCodeApi.describeThirdPartyAuthCode(authCode);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
