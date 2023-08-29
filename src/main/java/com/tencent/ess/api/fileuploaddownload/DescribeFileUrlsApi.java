package com.tencent.ess.api.fileuploaddownload;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.DescribeFileUrlsRequest;
import com.tencentcloudapi.ess.v20201111.models.DescribeFileUrlsResponse;
import com.tencentcloudapi.ess.v20201111.models.FileUrl;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;

public class DescribeFileUrlsApi {

    public static String DescribeFileUrls(String operatorId, String flowId) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        DescribeFileUrlsRequest request = new DescribeFileUrlsRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        request.setBusinessType("FLOW");
        request.setBusinessIds(new String[]{flowId});

        DescribeFileUrlsResponse response = client.DescribeFileUrls(request);

        FileUrl[] urls = response.getFileUrls();

        return urls[0].getUrl();
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            String flowId = "****************";

            String url = DescribeFileUrlsApi.DescribeFileUrls(Config.OperatorUserId, flowId);

            System.out.println("urls: " + url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
