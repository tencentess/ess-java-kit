package com.tencent.ess.api.sealmanage;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
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
public class DescribeOrganizationSealsApi {
    /**
     * 查询企业电子印章
     *
     * @param operatorId 经办人id
     * @param limit      返回最大数量，最大为100
     * @return DescribeOrganizationSealsResponse
     */
    public static DescribeOrganizationSealsResponse DescribeOrganizationSeals(String operatorId, long limit) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        DescribeOrganizationSealsRequest request = new DescribeOrganizationSealsRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        request.setLimit(limit);

        return client.DescribeOrganizationSeals(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            long limit = 10L;
            DescribeOrganizationSealsResponse response = DescribeOrganizationSealsApi.DescribeOrganizationSeals(Config.OperatorUserId, limit);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
