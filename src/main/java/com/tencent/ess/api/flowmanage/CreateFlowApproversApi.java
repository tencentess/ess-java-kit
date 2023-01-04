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

/**
 * 补充签署流程本企业签署人信息
 *
 * 官网文档：https://cloud.tencent.com/document/product/1323/80033
 *
 * 适用场景：在通过模版或者文件发起合同时，若未指定本企业签署人信息，则流程发起后，可以调用此接口补充签署人。
 * 同一签署人可以补充多个员工作为候选签署人,最终签署人取决于谁先领取合同完成签署。
 * <p>
 * 注：目前暂时只支持补充来源于企业微信的员工作为候选签署人
 */
public class CreateFlowApproversApi {
    /**
     * 补充签署流程本企业签署人信息
     *
     * @param operatorId 经办人id
     * @param flowId     流程id
     * @param approvers  补充签署人信息
     * @return CreateFlowApproversResponse
     */
    public static CreateFlowApproversResponse CreateFlowApprovers(String operatorId, String flowId, FillApproverInfo[] approvers) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreateFlowApproversRequest request = new CreateFlowApproversRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 签署流程编号
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
            // 签署人来源
            // WEWORKAPP: 企业微信
            approverInfo.setApproverSource("WEWORKAPP");
            // 签署人签署Id
            approverInfo.setRecipientId("****************");
            // 企业自定义账号Id
            // WEWORKAPP场景下指企业自有应用获取企微明文的userid
            approverInfo.setCustomUserId("***************");
            FillApproverInfo[] approvers = new FillApproverInfo[]{approverInfo};

            CreateFlowApproversResponse response = CreateFlowApproversApi.CreateFlowApprovers(Config.OperatorUserId, flowId, approvers);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}