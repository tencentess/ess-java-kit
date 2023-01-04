package com.tencent.ess.api.flowmanage;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.DescribeFlowBriefsRequest;
import com.tencentcloudapi.ess.v20201111.models.DescribeFlowBriefsResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

/**
 * 查询流程摘要.
 *
 * 官网文档：https://cloud.tencent.com/document/product/1323/70358
 *
 * 适用场景：可用于主动查询某个合同流程的签署状态信息。可以配合回调通知使用。
 */
public class DescribeFlowBriefsApi {
    /**
     * 查询流程摘要
     *
     * @param operatorId 经办人id
     * @param flowId     流程id
     * @return DescribeFlowBriefsResponse
     */
    public static DescribeFlowBriefsResponse DescribeFlowBriefs(String operatorId, String flowId) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        DescribeFlowBriefsRequest request = new DescribeFlowBriefsRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 需要查询的流程ID列表
        request.setFlowIds(new String[]{flowId});

        return client.DescribeFlowBriefs(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            String flowId = "****************";

            DescribeFlowBriefsResponse response = DescribeFlowBriefsApi.DescribeFlowBriefs(Config.OperatorUserId, flowId);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
