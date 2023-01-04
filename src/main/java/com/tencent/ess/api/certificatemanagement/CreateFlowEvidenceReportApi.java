package com.tencent.ess.api.certificatemanagement;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CreateFlowEvidenceReportRequest;
import com.tencentcloudapi.ess.v20201111.models.CreateFlowEvidenceReportResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

/**
 * 创建并返回出证报告
 *
 * 官网文档：https://cloud.tencent.com/document/product/1323/79686
 *
 * 创建出证报告，返回报告 ID。
 */
public class CreateFlowEvidenceReportApi {
    /**
     * 创建并返回出证报告
     *
     * @param flowId 签署流程编号
     */
    public static CreateFlowEvidenceReportResponse CreateFlowEvidenceReport(String operatorId, String flowId)
            throws Exception {

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreateFlowEvidenceReportRequest request = new CreateFlowEvidenceReportRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        request.setFlowId(flowId);

        return client.CreateFlowEvidenceReport(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            // 签署流程编号
            String flowId = "****************";

            CreateFlowEvidenceReportResponse response = CreateFlowEvidenceReportApi.CreateFlowEvidenceReport(Config.OperatorUserId, flowId);
            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

