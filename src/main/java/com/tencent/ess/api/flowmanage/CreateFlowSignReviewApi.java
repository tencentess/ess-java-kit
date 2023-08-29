package com.tencent.ess.api.flowmanage;

import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.*;

public class CreateFlowSignReviewApi {

    public static CreateFlowSignReviewResponse CreateFlowSignReview(String operatorId, String flowId, String reviewType,
                                                                    String reviewMessage) throws Exception {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreateFlowSignReviewRequest request = new CreateFlowSignReviewRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        request.setFlowId(flowId);
        request.setReviewType(reviewType);
        request.setReviewMessage(reviewMessage);

        CreateFlowSignReviewResponse resp = client.CreateFlowSignReview(request);
        return resp;
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            String flowId = "****************";

            String reviewType = "****************";

            String reviewMessage = "****************";

            CreateFlowSignReviewResponse resp = CreateFlowSignReviewApi.
                    CreateFlowSignReview(Config.OperatorUserId, flowId, reviewType, reviewMessage);
            System.out.println("requestId: " + resp.getRequestId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

