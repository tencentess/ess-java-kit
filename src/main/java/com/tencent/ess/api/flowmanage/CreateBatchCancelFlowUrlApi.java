package com.tencent.ess.api.flowmanage;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.*;

/**
 * 获取批量撤销签署流程链接
 * <p>
 * 官网地址：https://cloud.tencent.com/document/product/1323/78262
 * <p>
 * 电子签企业版：指定需要批量撤回的签署流程Id，获取批量撤销链接
 * 客户指定需要撤回的签署流程Id，最多100个，超过100不处理；接口调用成功返回批量撤回合同的链接，通过链接跳转到电子签小程序完成批量撤回
 */
public class CreateBatchCancelFlowUrlApi {
    /**
     * 获取批量撤销签署流程链接
     *
     * @param operatorId 经办人id
     * @param flowIds    流程id数组
     * @return CreateBatchCancelFlowUrlResponse
     */
    public static CreateBatchCancelFlowUrlResponse CreateBatchCancelFlowUrl(String operatorId, String[] flowIds) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreateBatchCancelFlowUrlRequest request = new CreateBatchCancelFlowUrlRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 	需要执行撤回的签署流程id数组，最多100个
        request.setFlowIds(flowIds);

        return client.CreateBatchCancelFlowUrl(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            // 需要执行撤回的签署流程id数组，最多100个
            String[] flowIds = new String[]{"****************", "****************"};

            CreateBatchCancelFlowUrlResponse response = CreateBatchCancelFlowUrlApi.CreateBatchCancelFlowUrl(Config.OperatorUserId, flowIds);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


