package com.tencent.ess.api.certificatemanagement;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.DescribeFlowEvidenceReportRequest;
import com.tencentcloudapi.ess.v20201111.models.DescribeFlowEvidenceReportResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

/**
 * 查询出证报告
 *
 * 官网文档：https://cloud.tencent.com/document/product/1323/83441
 *
 * 查询出证报告，返回报告 URL。
 */
public class DescribeFlowEvidenceReportApi {
    /**
     * 查询出证报告
     *
     * @param reportId 出证报告编号
     */
    public static DescribeFlowEvidenceReportResponse DescribeFlowEvidenceReport(String operatorId, String reportId)
            throws Exception {

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        DescribeFlowEvidenceReportRequest request = new DescribeFlowEvidenceReportRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        request.setReportId(reportId);

        return client.DescribeFlowEvidenceReport(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            // 出证报告编号
            String reportId = "****************";

            DescribeFlowEvidenceReportResponse response = DescribeFlowEvidenceReportApi.DescribeFlowEvidenceReport(Config.OperatorUserId, reportId);
            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

