package com.tencent.ess.api.documentmanagement;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.VerifyPdfRequest;
import com.tencentcloudapi.ess.v20201111.models.VerifyPdfResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

/**
 * 合同文件验签
 *
 * 官网文档：https://cloud.tencent.com/document/product/1323/80797
 *
 * 验证合同文件
 */
public class VerifyPdfApi {
    /**
     * 合同文件验签
     *
     * @param operatorId 经办人id
     * @param flowId     流程id
     * @return VerifyPdfResponse
     */
    public static VerifyPdfResponse VerifyPdf(String operatorId, String flowId) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        VerifyPdfRequest request = new VerifyPdfRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 合同Id，流程Id
        request.setFlowId(flowId);

        return client.VerifyPdf(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            String flowId = "****************";

            VerifyPdfResponse response = VerifyPdfApi.VerifyPdf(Config.OperatorUserId, flowId);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
