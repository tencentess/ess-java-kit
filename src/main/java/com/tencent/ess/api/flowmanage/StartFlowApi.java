package com.tencent.ess.api.flowmanage;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.StartFlowRequest;
import com.tencentcloudapi.ess.v20201111.models.StartFlowResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

/**
 * 此接口用于发起流程
 * 适用场景：见创建签署流程接口。
 * 注：该接口是“创建电子文档”接口的后置接口，用于激活包含完整合同信息（模板及内容信息）的流程。激活后的流程就是一份待签署的电子合同。
 */
public class StartFlowApi {
    /**
     * 发起流程
     * @param operatorId 经办人id
     * @param flowId     流程id
     * @return StartFlowResponse
     */
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
