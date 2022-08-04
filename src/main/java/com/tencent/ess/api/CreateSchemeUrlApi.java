package com.tencent.ess.api;

import com.google.gson.Gson;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CreateSchemeUrlRequest;
import com.tencentcloudapi.ess.v20201111.models.CreateSchemeUrlResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;

/**
 * 获取小程序跳转链接
 * 适用场景：如果需要签署人在自己的APP、小程序、H5应用中签署，可以通过此接口获取跳转腾讯电子签小程序的签署跳转链接。
 * 注：如果签署人是在PC端扫码签署，可以通过生成跳转链接自主转换成二维码，让签署人在PC端扫码签署。
 * 跳转到小程序的实现，参考官方文档（分为全屏、半屏两种方式）
 * 如您需要自主配置小程序跳转链接，请参考: 跳转小程序链接配置说明
 */
public class CreateSchemeUrlApi {
    /**
     * 获取小程序跳转链接
     *
     * @param operatorId 经办人id
     * @param flowId     流程id
     * @return CreateSchemeUrlResponse
     */
    public static CreateSchemeUrlResponse CreateSchemeUrl(String operatorId, String flowId) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreateSchemeUrlRequest request = new CreateSchemeUrlRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 签署流程编号 (PathType=1时必传)
        request.setFlowId(flowId);

        // 跳转页面 1: 小程序合同详情 2: 小程序合同列表页 0: 不传, 默认主页
        request.setPathType(1L);

        return client.CreateSchemeUrl(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            String flowId = "****************";

            CreateSchemeUrlResponse response = CreateSchemeUrlApi.CreateSchemeUrl(Config.OperatorUserId, flowId);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
