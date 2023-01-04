package com.tencent.ess.api.flowmanage;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.*;

/**
 * 查询合同详情
 *
 * 官网文档：https://cloud.tencent.com/document/product/1323/80032
 *
 * 适用场景：可用于主动查询某个合同详情信息。
 */
public class DescribeFlowInfoApi {
    /**
     * 查询合同详情
     *
     * @param operatorId 经办人id
     * @param flowId     流程id
     * @return DescribeFlowInfoResponse
     */
    public static DescribeFlowInfoResponse DescribeFlowInfo(String operatorId, String flowId) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        DescribeFlowInfoRequest request = new DescribeFlowInfoRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 需要查询的流程ID列表
        request.setFlowIds(new String[]{flowId});

        return client.DescribeFlowInfo(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            String flowId = "****************";

            DescribeFlowInfoResponse response = DescribeFlowInfoApi.DescribeFlowInfo(Config.OperatorUserId, flowId);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

