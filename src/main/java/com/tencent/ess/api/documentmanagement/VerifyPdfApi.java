package com.tencent.ess.api.documentmanagement;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.VerifyPdfRequest;
import com.tencentcloudapi.ess.v20201111.models.VerifyPdfResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

public class VerifyPdfApi {

    public static VerifyPdfResponse VerifyPdf(String operatorId, String flowId) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        VerifyPdfRequest request = new VerifyPdfRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

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
