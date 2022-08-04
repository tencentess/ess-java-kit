package com.tencent.ess.api;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.DescribeFileUrlsRequest;
import com.tencentcloudapi.ess.v20201111.models.DescribeFileUrlsResponse;
import com.tencentcloudapi.ess.v20201111.models.FileUrl;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;

/**
 * 功能描述：
 * 查询文件下载URL
 * 适用场景：通过传参合同流程编号，下载对应的合同PDF文件流到本地。
 */
public class DescribeFileUrlsApi {
    /**
     * 查询文件下载URL
     *
     * @param operatorId 经办人id
     * @param flowId     流程id
     * @return 文件url
     */
    public static String DescribeFileUrls(String operatorId, String flowId) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        DescribeFileUrlsRequest request = new DescribeFileUrlsRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 文件对应的业务类型，目前支持：
        // - 模板 "TEMPLATE"
        // - 文档 "DOCUMENT"
        // - 印章 “SEAL”
        // - 流程 "FLOW"
        request.setBusinessType("FLOW");

        // 业务编号的数组，如模板编号、文档编号、印章编号
        // 最大支持20个资源
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
