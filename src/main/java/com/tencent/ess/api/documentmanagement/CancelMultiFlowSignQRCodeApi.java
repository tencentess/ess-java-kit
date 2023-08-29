package com.tencent.ess.api.documentmanagement;

import com.google.gson.Gson;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CancelMultiFlowSignQRCodeRequest;
import com.tencentcloudapi.ess.v20201111.models.CancelMultiFlowSignQRCodeResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

public class CancelMultiFlowSignQRCodeApi {

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
