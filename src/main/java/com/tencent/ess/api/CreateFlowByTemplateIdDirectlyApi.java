package com.tencent.ess.api;

import com.tencent.ess.api.flowmanage.CreateDocumentApi;
import com.tencent.ess.api.flowmanage.CreateFlowApi;
import com.tencent.ess.api.flowmanage.CreateSchemeUrlApi;
import com.tencentcloudapi.ess.v20201111.models.CreateSchemeUrlResponse;
import com.tencentcloudapi.ess.v20201111.models.FlowCreateApprover;

import static com.tencent.ess.api.flowmanage.StartFlowApi.StartFlow;

/**
 * 通过模板发起签署流程，并查询签署链接
 */
public class CreateFlowByTemplateIdDirectlyApi {
    public static String[] CreateFlowByTemplateIdDirectly(String operatorId, String templateId,
                                                          String flowName, FlowCreateApprover[] approvers,
                                                          boolean isAutoSign) throws Exception {
        // 1、创建流程
        String flowId = CreateFlowApi.CreateFlow(operatorId, flowName, approvers, isAutoSign);

        // 2、创建电子文档
        // --- 注意 formFields 与模板保持一样  CreateDocument.packFormFieldsExample()
        CreateDocumentApi.CreateDocument(operatorId, flowId, templateId, flowName,
                CreateDocumentApi.PackFormFieldsExample());

        // 3、等待文档异步合成
        Thread.sleep(3000);

        // 4、开启流程
        StartFlow(operatorId, flowId);

        // 5、获取签署链接
        CreateSchemeUrlResponse response = CreateSchemeUrlApi.CreateSchemeUrl(operatorId, flowId);

        return new String[]{flowId, response.getSchemeUrl()};
    }
}