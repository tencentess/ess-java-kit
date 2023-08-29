package com.tencent.ess.autosign;

import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.DisableUserAutoSignRequest;
import com.tencentcloudapi.ess.v20201111.models.DisableUserAutoSignResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

public class DisableUserAutoSignApi {

    public static void main(String[] args) {
        DisableUserAutoSignRequest req = prepareDisableUserAutoSignRequest();
        req.setUserInfo(CreateUserAutoSignEnableUrlApi.PrepareUserThreeFactor("姓名", "身份证号"));

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        try {
            DisableUserAutoSignResponse res = client.DisableUserAutoSign(req);
            assert res != null;
            System.out.println(DisableUserAutoSignResponse.toJsonString(res));
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构造请求基本参数
     */
    public static DisableUserAutoSignRequest prepareDisableUserAutoSignRequest() {

        DisableUserAutoSignRequest req = new DisableUserAutoSignRequest();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(Config.OperatorUserId);
        req.setOperator(userInfo);
        req.setSceneKey("E_PRESCRIPTION_AUTO_SIGN");
        return req;
    }
}
