package com.tencent.ess.api.flowmanage;

import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.*;

/**
 * 提交企业签署流程审批结果
 *
 * 官网地址：https://cloud.tencent.com/document/product/1323/78980
 *
 * 在通过接口(CreateFlow 或者CreateFlowByFiles)创建签署流程时，若指定了参数 NeedSignReview 为true,则可以调用此
 * 接口提交企业内部签署审批结果。若签署流程状态正常，且本企业存在签署方未签署，同一签署流程可以多次提交签署审批结果，签署时的
 * 最后一个“审批结果”有效。
 */
public class CreateFlowSignReviewApi {
    /**
     * 提交企业签署流程审批结果
     *
     * @param operatorId    经办人id
     * @param flowId        流程id
     * @param reviewType    企业内部审核结果
     * @param reviewMessage 审核原因
     * @return CreateFlowSignReviewResponse
     */
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

        // 签署流程名称,最大长度200个字符
        request.setFlowId(flowId);

        // 	企业内部审核结果
        //PASS: 通过
        //REJECT: 拒绝
        request.setReviewType(reviewType);

        // 审核原因
        //当ReviewType 是REJECT 时此字段必填,字符串长度不超过200
        request.setReviewMessage(reviewMessage);

        CreateFlowSignReviewResponse resp = client.CreateFlowSignReview(request);
        return resp;
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            // 签署流程编号，由CreateFlow/CreateFlowByFiles接口返回
            String flowId = "****************";

            // 企业内部审核结果
            // PASS: 通过
            // REJECT: 拒绝
            String reviewType = "****************";

            // 审核原因
            // 当ReviewType 是REJECT 时此字段必填,字符串长度不超过200
            String reviewMessage = "****************";

            CreateFlowSignReviewResponse resp = CreateFlowSignReviewApi.
                    CreateFlowSignReview(Config.OperatorUserId, flowId, reviewType, reviewMessage);
            System.out.println("requestId: " + resp.getRequestId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

