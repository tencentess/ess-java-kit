package com.tencent.ess.api.flowmanage;

import com.google.gson.Gson;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CreateSchemeUrlRequest;
import com.tencentcloudapi.ess.v20201111.models.CreateSchemeUrlResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;

public class CreateSchemeUrlApi {

    public static CreateSchemeUrlResponse CreateSchemeUrl(String operatorId, String flowId) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreateSchemeUrlRequest request = new CreateSchemeUrlRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        request.setFlowId(flowId);

        request.setPathType(1L);

        return client.CreateSchemeUrl(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            String flowId = "****************";

            CreateSchemeUrlResponse response = CreateSchemeUrlApi.CreateSchemeUrl(Config.OperatorUserId, flowId);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
