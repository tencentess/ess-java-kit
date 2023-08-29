package com.tencent.ess.api.organizationmanagement;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.*;

public class DeleteIntegrationEmployeesApi {

    public static DeleteIntegrationEmployeesResponse DeleteIntegrationEmployees(String operatorId, Staff[] employees) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        DeleteIntegrationEmployeesRequest request = new DeleteIntegrationEmployeesRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        request.setEmployees(employees);

        return client.DeleteIntegrationEmployees(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            Staff employee = new Staff();
            employee.setUserId("************");

            DeleteIntegrationEmployeesResponse response = DeleteIntegrationEmployeesApi.
                    DeleteIntegrationEmployees(Config.OperatorUserId, new Staff[]{employee});

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
