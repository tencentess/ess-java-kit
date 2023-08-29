package com.tencent.ess.api.flowmanage;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CreateFlowApproversRequest;
import com.tencentcloudapi.ess.v20201111.models.CreateFlowApproversResponse;
import com.tencentcloudapi.ess.v20201111.models.FillApproverInfo;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

public class CreateFlowApproversApi {

    public static CreateFlowApproversResponse CreateFlowApprovers(String operatorId, String flowId, FillApproverInfo[] approvers) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreateFlowApproversRequest request = new CreateFlowApproversRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        request.setFlowId(flowId);

        request.setApprovers(approvers);

        return client.CreateFlowApprovers(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            // 签署流程编号
            String flowId = "*****************";

            // 补充签署人信息
            FillApproverInfo approverInfo = new FillApproverInfo();

            approverInfo.setApproverSource("WEWORKAPP");

            approverInfo.setRecipientId("****************");

            approverInfo.setCustomUserId("***************");
            FillApproverInfo[] approvers = new FillApproverInfo[]{approverInfo};

            CreateFlowApproversResponse response = CreateFlowApproversApi.CreateFlowApprovers(Config.OperatorUserId, flowId, approvers);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}