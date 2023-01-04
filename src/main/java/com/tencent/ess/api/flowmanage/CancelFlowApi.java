package com.tencent.ess.api.flowmanage;

import com.google.gson.Gson;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CancelFlowRequest;
import com.tencentcloudapi.ess.v20201111.models.CancelFlowResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

/**
 * 撤销签署流程
 * <p>
 * 官网文档：https://cloud.tencent.com/document/product/1323/70362
 * <p>
 * 适用场景：如果某个合同流程当前至少还有一方没有签署，则可通过该接口取消该合同流程。常用于合同发错、内容填错，需要及时撤销的场景。
 * 注：如果合同流程中的参与方均已签署完毕，则无法通过该接口撤销合同。
 */
public class CancelFlowApi {
    /**
     * 撤销签署流程
     *
     * @param operatorId    经办人id
     * @param flowId        流程id
     * @param cancelMessage 撤销原因，最长200个字符
     * @return CancelFlowResponse
     */
    public static CancelFlowResponse CancelFlow(String operatorId, String flowId, String cancelMessage)
            throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = com.tencent.ess.common.Client.getEssClient();

        // 构造请求体
        CancelFlowRequest request = new CancelFlowRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 签署流程id
        request.setFlowId(flowId);

        // 撤销原因，最长200个字符
        request.setCancelMessage(cancelMessage);

        // 如果接口报错，会抛出TencentCloudSDKException，不会输出response，请捕获TencentCloudSDKException，参考main函数

        return client.CancelFlow(request);

    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            // 签署流程id
            String flowId = "****************";

            // 撤销原因，最长200个字符
            String cancelMessage = "撤销原因";

            CancelFlowResponse response = CancelFlowApi.CancelFlow(Config.OperatorUserId, flowId, cancelMessage);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
