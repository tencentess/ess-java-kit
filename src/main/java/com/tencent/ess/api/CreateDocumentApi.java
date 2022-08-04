package com.tencent.ess.api;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CreateDocumentRequest;
import com.tencentcloudapi.ess.v20201111.models.CreateDocumentResponse;
import com.tencentcloudapi.ess.v20201111.models.FormField;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

/**
 * 创建电子文档
 * 适用场景：见创建签署流程接口。
 * 注：该接口需要给对应的流程指定一个模板id，并且填充该模板中需要补充的信息。是“发起流程”接口的前置接口。
 */
public class CreateDocumentApi {
    /**
     * 创建电子文档   --请注意设置flowId参与者、FormField与模板保持一致
     *
     * @param operatorId 经办人id
     * @param flowId     流程id
     * @param templateId 单个个人签署模版
     * @param fileName   单个文件名,最大长度200个字符
     * @param formFields 设置发起人填写控件
     * @return CreateDocumentResponse
     */
    public static CreateDocumentResponse CreateDocument(String operatorId, String flowId, String templateId, String fileName,
                                                        FormField[] formFields)
            throws TencentCloudSDKException {

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();
        CreateDocumentRequest request = new CreateDocumentRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 流程id
        request.setFlowId(flowId);

        // 用户上传的模板ID,在控制台模版管理中可以找到
        // 单个个人签署模版
        request.setTemplateId(templateId);

        // 文件名列表,单个文件名最大长度200个字符
        String[] fileNames = new String[]{fileName};
        request.setFileNames(fileNames);

        //设置发起人填写控件
        if (formFields != null && formFields.length > 0) {
            request.setFormFields(formFields);
        }

        return client.CreateDocument(request);
    }

    /**
     * 请根据实际情况组装FormField，请注意与模板保持一致
     * @return FormField数组
     */
    public static FormField[] PackFormFieldsExample() {

        // 单行文本类型赋值 文本内容
        FormField text = new FormField();
        text.setComponentName("单行文本1");
        text.setComponentValue("单行文本内容");

        // 单行文本类型赋值 文本内容
        FormField multiLineText = new FormField();
        multiLineText.setComponentName("多行文本1");
        multiLineText.setComponentValue("多行文本内容");

        // 勾选框类型赋值 true/false
        FormField checkbox = new FormField();
        checkbox.setComponentName("勾选框1");
        checkbox.setComponentValue("true");

        // 选择器类型赋值 控制台选项值
        FormField selector = new FormField();
        selector.setComponentName("选择器1");
        selector.setComponentValue("选项一");

        // 附件类型赋值 UploadFiles接口上传返回的fileId
        FormField attachment = new FormField();
        attachment.setComponentName("详见附件1");
        attachment.setComponentValue("****************");

        return new FormField[]{text, multiLineText, checkbox, selector, attachment};
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            String flowId = "****************";
            String fileName = "****************";

            // --- 注意 formFields 与模板保持一样  CreateDocument.packFormFieldsExample()
            // 若模板无 formFields，请改为 CreateDocument.createDocument(Config.OperatorId, flowId, Config.TemplateId, fileName,null)
            CreateDocumentResponse response = CreateDocumentApi.CreateDocument(Config.OperatorUserId, flowId, Config.TemplateId, fileName,
                    PackFormFieldsExample());

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
