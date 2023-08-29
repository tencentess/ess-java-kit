package com.tencent.ess.api.templatemanagement;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.DescribeFlowTemplatesRequest;
import com.tencentcloudapi.ess.v20201111.models.DescribeFlowTemplatesResponse;
import com.tencentcloudapi.ess.v20201111.models.Filter;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

public class DescribeFlowTemplatesApi {

    public static DescribeFlowTemplatesResponse describeFlowTemplates(String operatorId, String templateId) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        DescribeFlowTemplatesRequest request = new DescribeFlowTemplatesRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        Filter filter = new Filter();
        filter.setKey("template-id");
        filter.setValues(new String[]{templateId});
        request.setFilters(new Filter[]{filter});

        return client.DescribeFlowTemplates(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            DescribeFlowTemplatesResponse response = DescribeFlowTemplatesApi.describeFlowTemplates(Config.OperatorUserId, Config.TemplateId);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
