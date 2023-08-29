package com.tencent.ess.api.flowmanage;

import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CreatePrepareFlowRequest;
import com.tencentcloudapi.ess.v20201111.models.CreatePrepareFlowResponse;
import com.tencentcloudapi.ess.v20201111.models.FlowCreateApprover;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

public class CreatePrepareFlowApi {

    public static CreatePrepareFlowResponse CreatePrepareFlow(String operatorId, String flowName, String resourceId, FlowCreateApprover[] approvers)
            throws Exception {

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreatePrepareFlowRequest request = new CreatePrepareFlowRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        request.setFlowName(flowName);

        request.setApprovers(approvers);

        request.setResourceId(resourceId);

        return client.CreatePrepareFlow(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            String flowName = "****************";
            String resourceId = "y************0s";

            FlowCreateApprover personInfo = new FlowCreateApprover();
            personInfo.setApproverType(1L);
            personInfo.setApproverName("李四");
            personInfo.setApproverMobile("1*********4");

            FlowCreateApprover[] approvers = new FlowCreateApprover[]{personInfo};

            CreatePrepareFlowResponse resp = CreatePrepareFlowApi.CreatePrepareFlow(Config.OperatorUserId, flowName, resourceId, approvers);
            System.out.println("url: " + resp.getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
