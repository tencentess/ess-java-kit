package com.tencent.ess.api.organizationmanagement;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CreateIntegrationEmployeesRequest;
import com.tencentcloudapi.ess.v20201111.models.CreateIntegrationEmployeesResponse;
import com.tencentcloudapi.ess.v20201111.models.Staff;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

public class CreateIntegrationEmployeesApi {

    public static CreateIntegrationEmployeesResponse CreateIntegrationEmployees(String operatorId, Staff[] employees) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreateIntegrationEmployeesRequest request = new CreateIntegrationEmployeesRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        request.setEmployees(employees);

        return client.CreateIntegrationEmployees(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            Staff employee = new Staff();
            employee.setDisplayName("张三");
            employee.setMobile("***********");

            CreateIntegrationEmployeesResponse response = CreateIntegrationEmployeesApi.
                    CreateIntegrationEmployees(Config.OperatorUserId, new Staff[]{employee});

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
