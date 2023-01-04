package com.tencent.ess.api.fileuploaddownload;


import com.google.gson.Gson;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CreateConvertTaskApiRequest;
import com.tencentcloudapi.ess.v20201111.models.CreateConvertTaskApiResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

/**
 * 创建文件转换任务
 * <p>
 * 官网文档：https://cloud.tencent.com/document/product/1323/78149
 * <p>
 * 创建文件转换任务
 */
public class CreateConvertTaskApi {
    /**
     * 撤销签署流程
     *
     * @param operatorId   经办人id
     * @param resourceId   资源Id，通过UploadFiles获取
     * @param resourceType 资源类型 取值范围doc,docx,html,xls,xlsx之一
     * @param resourceName 资源名称，长度限制为256字符
     * @return CreateConvertTaskApiResponse
     */
    public static CreateConvertTaskApiResponse CreateConvertTaskApi(String operatorId, String resourceId, String resourceType, String resourceName)
            throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = com.tencent.ess.common.Client.getEssClient();

        // 构造请求体
        CreateConvertTaskApiRequest request = new CreateConvertTaskApiRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 资源类型 取值范围doc,docx,html,xls,xlsx之一
        request.setResourceType(resourceType);

        // 资源名称，长度限制为256字符
        request.setResourceName(resourceName);

        // 资源Id，通过UploadFiles获取
        request.setResourceId(resourceId);

        return client.CreateConvertTaskApi(request);

    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            // 资源Id，通过UploadFiles获取
            String resourceId = "****************";
            // 资源类型 取值范围doc,docx,html,xls,xlsx之一
            String resourceType = "****************";
            // 资源名称，长度限制为256字符
            String resourceName = "****************";

            CreateConvertTaskApiResponse response = CreateConvertTaskApi.CreateConvertTaskApi(Config.OperatorUserId, resourceId, resourceType, resourceName);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
