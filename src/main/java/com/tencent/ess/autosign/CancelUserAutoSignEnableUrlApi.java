package com.tencent.ess.autosign;

import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CancelUserAutoSignEnableUrlRequest;
import com.tencentcloudapi.ess.v20201111.models.CancelUserAutoSignEnableUrlResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;
import com.tencentcloudapi.ess.v20201111.models.UserThreeFactor;

public class CancelUserAutoSignEnableUrlApi {

    public static final String sceneKey = "E_PRESCRIPTION_AUTO_SIGN";

    public static void main(String[] args) {

        // 构造撤销请求参数
        CancelUserAutoSignEnableUrlRequest request = buildCancelUserAutoSignEnableUrlRequest("姓名", "身份证号");

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        try {
            // 调用撤销自动签链接接口
            CancelUserAutoSignEnableUrlResponse response = client.CancelUserAutoSignEnableUrl(request);
            assert response != null;
            System.out.println(CancelUserAutoSignEnableUrlResponse.toJsonString(response));
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }

    /*
     * 构造请求参数
     */
    public static CancelUserAutoSignEnableUrlRequest buildCancelUserAutoSignEnableUrlRequest(
            String name, String idCardNumber) {

        CancelUserAutoSignEnableUrlRequest request = new CancelUserAutoSignEnableUrlRequest();

        // 构造公共参数
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(Config.OperatorUserId);

        // 指定自动签链接用户
        UserThreeFactor threeFactor = CreateUserAutoSignEnableUrlApi.PrepareUserThreeFactor(name, idCardNumber);

        request.setOperator(userInfo);
        request.setSceneKey(sceneKey);
        request.setUserInfo(threeFactor);
        return request;
    }

}