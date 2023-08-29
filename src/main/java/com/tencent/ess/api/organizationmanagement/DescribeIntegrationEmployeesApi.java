package com.tencent.ess.api.organizationmanagement;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.Filter;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;
import com.tencentcloudapi.ess.v20201111.models.DescribeIntegrationEmployeesRequest;
import com.tencentcloudapi.ess.v20201111.models.DescribeIntegrationEmployeesResponse;

public class DescribeIntegrationEmployeesApi {

    public static DescribeIntegrationEmployeesResponse DescribeIntegrationEmployees(String operatorId, long limit, long offset, Filter[] filters) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        DescribeIntegrationEmployeesRequest request = new DescribeIntegrationEmployeesRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        request.setFilters(filters);

        request.setLimit(limit);

        request.setOffset(offset);

        return client.DescribeIntegrationEmployees(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            long limit = 20;
            long offset = 0;
            Filter filter = new Filter();
            filter.setKey("Status");
            filter.setValues(new String[]{"IsVerified"});

            DescribeIntegrationEmployeesResponse response = DescribeIntegrationEmployeesApi.
                    DescribeIntegrationEmployees(Config.OperatorUserId, limit, offset, new Filter[]{filter});

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
