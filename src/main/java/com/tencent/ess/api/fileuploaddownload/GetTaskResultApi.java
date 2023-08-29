package com.tencent.ess.api.fileuploaddownload;


import com.google.gson.Gson;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.GetTaskResultApiRequest;
import com.tencentcloudapi.ess.v20201111.models.GetTaskResultApiResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

public class GetTaskResultApi {

    public static GetTaskResultApiResponse GetTaskResult(String operatorId, String taskId)
            throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = com.tencent.ess.common.Client.getEssClient();

        // 构造请求体
        GetTaskResultApiRequest request = new GetTaskResultApiRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        request.setTaskId(taskId);

        return client.GetTaskResultApi(request);

    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            // 任务Id，通过CreateConvertTaskApi得到
            String taskId = "****************";

            GetTaskResultApiResponse response = GetTaskResultApi.GetTaskResult(Config.OperatorUserId, taskId);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
