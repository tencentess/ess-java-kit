package com.tencent.ess.group;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.*;

/**
 * 查询集团企业列表
 * <p>
 * 官网文档：https://cloud.tencent.com/document/product/1323/86114
 * <p>
 * 此API接口用户查询加入集团的成员企业
 */
public class GroupDescribeOrganizationGroupOrganizations {

    /**
     * 通过上传后的pdf资源编号来创建待签署的合同流程
     *
     * @param operatorId 经办人id
     * @param limit      返回最大数量
     * @param offset     偏移量
     */
    public static DescribeOrganizationGroupOrganizationsResponse DescribeOrganizationGroupOrganizations(String operatorId, Long limit, Long offset)
            throws TencentCloudSDKException {

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        DescribeOrganizationGroupOrganizationsRequest request = new DescribeOrganizationGroupOrganizationsRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        userInfo.setClientIp("8.8.8.8");
        userInfo.setChannel("YUFU");
        request.setOperator(userInfo);

        // 返回最大数量
        request.setLimit(limit);
        // 查询偏移量
        request.setOffset(offset);

        return client.DescribeOrganizationGroupOrganizations(request);
    }

    /**
     * 查询集团企业列表
     */
    public static void main(String[] args) {
        try {
            Long limit = 10L;
            Long offset = 0L;

            DescribeOrganizationGroupOrganizationsResponse response = DescribeOrganizationGroupOrganizations(Config.OperatorUserId, limit, offset);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
