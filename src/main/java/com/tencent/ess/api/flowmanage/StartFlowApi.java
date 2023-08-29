package com.tencent.ess.api.flowmanage;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.StartFlowRequest;
import com.tencentcloudapi.ess.v20201111.models.StartFlowResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

public class StartFlowApi {

    public static StartFlowResponse StartFlow(String operatorId, String flowId) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        StartFlowRequest request = new StartFlowRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 签署流程编号，由CreateFlow接口返回
        request.setFlowId(flowId);

        return client.StartFlow(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            // 签署流程编号，由CreateFlow接口返回
            String flowId = "****************";

            StartFlowResponse response = StartFlowApi.StartFlow(Config.OperatorUserId, flowId);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
