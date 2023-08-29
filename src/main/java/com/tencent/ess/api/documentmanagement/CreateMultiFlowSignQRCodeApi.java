package com.tencent.ess.api.documentmanagement;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CreateMultiFlowSignQRCodeRequest;
import com.tencentcloudapi.ess.v20201111.models.CreateMultiFlowSignQRCodeResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

public class CreateMultiFlowSignQRCodeApi {

    public static CreateMultiFlowSignQRCodeResponse CreateMultiFlowSignQRCode(String operatorId, String templateId, String flowName)
            throws TencentCloudSDKException {

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreateMultiFlowSignQRCodeRequest request = new CreateMultiFlowSignQRCodeRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        request.setTemplateId(templateId);
        request.setFlowName(flowName);

        return client.CreateMultiFlowSignQRCode(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            String flowName = "****************";

            CreateMultiFlowSignQRCodeResponse response = CreateMultiFlowSignQRCodeApi.CreateMultiFlowSignQRCode(Config.OperatorUserId, Config.TemplateId, flowName);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
