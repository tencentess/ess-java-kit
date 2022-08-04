package com.tencent.ess.api;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CreateMultiFlowSignQRCodeRequest;
import com.tencentcloudapi.ess.v20201111.models.CreateMultiFlowSignQRCodeResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

/**
 * 此接口（CreateMultiFlowSignQRCode）用于创建一码多扫流程签署二维码。
 * 适用场景：无需填写签署人信息，可通过模板id生成签署二维码，签署人可通过扫描二维码补充签署信息进行实名签署。常用于提前不知道签署人的身份信息场景，例如：劳务工招工、大批量员工入职等场景。
 * 适用的模板仅限于B2C（1、无序签署，2、顺序签署时B静默签署，3、顺序签署时B非首位签署）、单C的模板，且模板中发起方没有填写控件。
 */
public class CreateMultiFlowSignQRCodeApi {

    /**
     * 创建一码多扫流程签署二维码
     *
     * @param operatorId 经办人id
     * @param templateId 模板ID
     * @param flowName   流程名
     * @return CreateMultiFlowSignQRCodeResponse
     */
    public static CreateMultiFlowSignQRCodeResponse CreateMultiFlowSignQRCode(String operatorId, String templateId, String flowName)
            throws TencentCloudSDKException {

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreateMultiFlowSignQRCodeRequest request = new CreateMultiFlowSignQRCodeRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 模板ID
        request.setTemplateId(templateId);
        // 签署流程名称，最大长度不超过200字符
        request.setFlowName(flowName);

        return client.CreateMultiFlowSignQRCode(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            String flowName = "****************";

            CreateMultiFlowSignQRCodeResponse response = CreateMultiFlowSignQRCodeApi.CreateMultiFlowSignQRCode(Config.OperatorUserId, Config.TemplateId, flowName);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
