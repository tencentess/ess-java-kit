package com.tencent.ess.group;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.Agent;
import com.tencentcloudapi.ess.v20201111.models.CreateSchemeUrlRequest;
import com.tencentcloudapi.ess.v20201111.models.CreateSchemeUrlResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

/**
 * 获取小程序跳转链接
 * <p>
 * 官网文档： https://cloud.tencent.com/document/product/1323/70359
 * <p>
 * 适用场景：如果需要签署人在自己的APP、小程序、H5应用中签署，可以通过此接口获取跳转腾讯电子签小程序的签署跳转链接。
 * 注：如果签署人是在PC端扫码签署，可以通过生成跳转链接自主转换成二维码，让签署人在PC端扫码签署。
 * 跳转到小程序的实现，参考官方文档（分为全屏、半屏两种方式）
 * 如您需要自主配置小程序跳转链接，请参考: 跳转小程序链接配置说明
 */
public class GroupCreateSchemeUrl {
    /**
     * 获取小程序跳转链接
     *
     * @param operatorId          经办人id
     * @param proxyOrganizationId 代发起子企业的企业id
     * @param flowId              流程id
     * @return CreateSchemeUrlResponse
     */
    public static CreateSchemeUrlResponse CreateSchemeUrl(String operatorId, String proxyOrganizationId, String flowId) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreateSchemeUrlRequest request = new CreateSchemeUrlRequest();

        // 设置集团子企业账号
        Agent agent = new Agent();
        agent.setProxyOrganizationId(proxyOrganizationId);
        request.setAgent(agent);

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
     * 主企业代子企业获取链接样例
     * 注意：使用集团代发起功能，需要主企业和子企业均已加入集团，并且主企业OperatorUserId对应人员被赋予了对应操作权限
     */
    public static void main(String[] args) {
        try {
            // 需要代发起的子企业的企业id
            String proxyOrganizationId = "*****************";
            // 子企业合同id
            String flowId = "**************";

            CreateSchemeUrlResponse response = CreateSchemeUrl(Config.OperatorUserId, proxyOrganizationId, flowId);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
