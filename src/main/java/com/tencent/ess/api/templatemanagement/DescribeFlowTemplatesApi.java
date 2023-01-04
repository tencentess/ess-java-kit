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

/**
 * 查询模板
 *
 * 官网文档：https://cloud.tencent.com/document/product/1323/74803
 *
 * 适用场景：当模板较多或模板中的控件较多时，可以通过查询模板接口更方便的获取自己主体下的模板列表，以及每个模板内的控件信息。
 * 该接口常用来配合“创建电子文档”接口作为前置的接口使用。
 */
public class DescribeFlowTemplatesApi {
    /**
     * 查询模板
     *
     * @param operatorId 经办人id
     * @param templateId 流程id
     * @return DescribeFlowTemplatesResponse
     */
    public static DescribeFlowTemplatesResponse describeFlowTemplates(String operatorId, String templateId) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        DescribeFlowTemplatesRequest request = new DescribeFlowTemplatesRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 需要查询的流程ID列表
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
