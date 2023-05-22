package com.tencent.ess.group;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.Agent;
import com.tencentcloudapi.ess.v20201111.models.DescribeFlowInfoRequest;
import com.tencentcloudapi.ess.v20201111.models.DescribeFlowInfoResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

/**
 * 查询合同详情
 * <p>
 * 官网文档：https://cloud.tencent.com/document/product/1323/80032
 * <p>
 * 适用场景：可用于主动查询某个合同详情信息。
 */
public class GroupDescribeFlowInfo {
    /**
     * 查询合同详情
     *
     * @param operatorId          经办人id
     * @param proxyOrganizationId 代发起子企业的企业id
     * @param flowId              流程id
     * @return DescribeFlowInfoResponse
     */
    public static DescribeFlowInfoResponse DescribeFlowInfo(String operatorId, String proxyOrganizationId, String flowId) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        DescribeFlowInfoRequest request = new DescribeFlowInfoRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 设置集团子企业账号
        Agent agent = new Agent();
        agent.setProxyOrganizationId(proxyOrganizationId);
        request.setAgent(agent);

        // 需要查询的流程ID列表
        request.setFlowIds(new String[]{flowId});

        return client.DescribeFlowInfo(request);
    }

    /**
     * 主企业代子企业查询合同信息的使用样例
     * 注意：使用集团代发起功能，需要主企业和子企业均已加入集团，并且主企业OperatorUserId对应人员被赋予了对应操作权限
     */
    public static void main(String[] args) {
        try {
            // 需要代发起的子企业的企业id
            String proxyOrganizationId = "*****************";
            // 子企业的合同id
            String flowId = "***************";

            DescribeFlowInfoResponse response = DescribeFlowInfo(Config.OperatorUserId, proxyOrganizationId, flowId);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

