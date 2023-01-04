package com.tencent.ess.api.documentmanagement;

import com.google.gson.Gson;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CancelMultiFlowSignQRCodeRequest;
import com.tencentcloudapi.ess.v20201111.models.CancelMultiFlowSignQRCodeResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

/**
 * 取消一码多扫二维码。该接口对传入的二维码ID，若还在有效期内，可以提前失效。
 *
 * 官网文档：https://cloud.tencent.com/document/product/1323/75451
 */
public class CancelMultiFlowSignQRCodeApi {
    /**
     * 此接口（CancelMultiFlowSignQRCode）用于取消一码多扫二维码。该接口对传入的二维码ID，若还在有效期内，可以提前失效。
     *
     * @param operatorId 经办人id
     * @param qrCodeId   二维码id
     * @return CancelMultiFlowSignQRCodeResponse
     */
    public static CancelMultiFlowSignQRCodeResponse CancelMultiFlowSignQRCode(String operatorId, String qrCodeId)
            throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = com.tencent.ess.common.Client.getEssClient();

        // 构造请求体
        CancelMultiFlowSignQRCodeRequest request = new CancelMultiFlowSignQRCodeRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 二维码id
        request.setQrCodeId(qrCodeId);

        return client.CancelMultiFlowSignQRCode(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            String qrCodeId = "****************";

            CancelMultiFlowSignQRCodeResponse response = CancelMultiFlowSignQRCodeApi.CancelMultiFlowSignQRCode(Config.OperatorUserId, qrCodeId);

            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
