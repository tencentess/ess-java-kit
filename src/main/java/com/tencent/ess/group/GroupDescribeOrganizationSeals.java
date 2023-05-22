package com.tencent.ess.group;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.Agent;
import com.tencentcloudapi.ess.v20201111.models.DescribeOrganizationSealsRequest;
import com.tencentcloudapi.ess.v20201111.models.DescribeOrganizationSealsResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

/**
 * 查询企业电子印章
 * <p>
 * 官网文档：https://cloud.tencent.com/document/product/1323/82453
 * <p>
 * 查询企业印章的列表，需要操作者具有查询印章权限
 */
public class GroupDescribeOrganizationSeals {
    /**
     * 查询企业电子印章
     *
     * @param operatorId          经办人id
     * @param proxyOrganizationId 代发起子企业的企业id
     * @param limit               返回最大数量，最大为100
     * @return DescribeOrganizationSealsResponse
     */
    public static DescribeOrganizationSealsResponse DescribeOrganizationSeals(String operatorId, String proxyOrganizationId, long limit) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        DescribeOrganizationSealsRequest request = new DescribeOrganizationSealsRequest();

        // 设置集团子企业账号
        Agent agent = new Agent();
        agent.setProxyOrganizationId(proxyOrganizationId);
        request.setAgent(agent);

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        request.setLimit(limit);

        return client.DescribeOrganizationSeals(request);
    }

    /**
     * 主企业代子企业查询企业印章信息样例
     * 注意：使用集团代发起功能，需要主企业和子企业均已加入集团，并且主企业OperatorUserId对应人员被赋予了对应操作权限
     */
    public static void main(String[] args) {
        try {
            // 需要代发起的子企业的企业id
            String proxyOrganizationId = "*****************";

            long limit = 10L;
            DescribeOrganizationSealsResponse response = DescribeOrganizationSeals(Config.OperatorUserId, proxyOrganizationId, limit);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
