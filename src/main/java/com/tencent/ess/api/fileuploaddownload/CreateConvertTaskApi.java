package com.tencent.ess.api.fileuploaddownload;


import com.google.gson.Gson;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CreateConvertTaskApiRequest;
import com.tencentcloudapi.ess.v20201111.models.CreateConvertTaskApiResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

public class CreateConvertTaskApi {

    public static CreateConvertTaskApiResponse CreateConvertTask(String operatorId, String resourceId, String resourceType, String resourceName)
            throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = com.tencent.ess.common.Client.getEssClient();

        // 构造请求体
        CreateConvertTaskApiRequest request = new CreateConvertTaskApiRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        request.setResourceType(resourceType);
        request.setResourceName(resourceName);
        request.setResourceId(resourceId);

        return client.CreateConvertTaskApi(request);

    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            String resourceId = "****************";

            String resourceType = "****************";

            String resourceName = "****************";

            CreateConvertTaskApiResponse response = CreateConvertTaskApi.CreateConvertTask(Config.OperatorUserId, resourceId, resourceType, resourceName);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
