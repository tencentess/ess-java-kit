package com.tencent.ess.autosign;

import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.*;

public class CreateUserAutoSignEnableUrlApi {

    public static void main(String[] args) {

        CreateUserAutoSignEnableUrlRequest req = prepareCreateUserAutoSignEnableUrlRequest("");

        AutoSignConfig autoSignConfig = PrepareUserAutoSignConfig(
                PrepareUserThreeFactor("姓名", "身份证号"),
                "https://example.com", true, true, true);

        req.setAutoSignConfig(autoSignConfig);

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        try {
            CreateUserAutoSignEnableUrlResponse res = client.CreateUserAutoSignEnableUrl(req);
            assert res != null;
            System.out.println(CreateUserAutoSignEnableUrlResponse.toJsonString(res));
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }

    public static UserThreeFactor PrepareUserThreeFactor(String name, String idCardNum) {
        UserThreeFactor threeFactor = new UserThreeFactor();
        // 姓名
        threeFactor.setName(name);
        // 身份证号
        threeFactor.setIdCardNumber(idCardNum);
        threeFactor.setIdCardType("ID_CARD");
        return threeFactor;
    }

    public static AutoSignConfig PrepareUserAutoSignConfig(
            UserThreeFactor threeFactor,
            String callBackUrl,
            boolean selfDefineSeal,
            boolean needSealImgCallback,
            boolean needCertInfoCallback
            ) {
        AutoSignConfig autoSignConfig = new AutoSignConfig();
        autoSignConfig.setUserInfo(threeFactor);

        autoSignConfig.setCallbackUrl(callBackUrl);

        autoSignConfig.setUserDefineSeal(selfDefineSeal);

        autoSignConfig.setSealImgCallback(needSealImgCallback);

        autoSignConfig.setCertInfoCallback(needCertInfoCallback);
        return autoSignConfig;
    }

    public static CreateUserAutoSignEnableUrlRequest prepareCreateUserAutoSignEnableUrlRequest(String urlType) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(Config.OperatorUserId);

        CreateUserAutoSignEnableUrlRequest req = new CreateUserAutoSignEnableUrlRequest();

        req.setOperator(userInfo);

        req.setUrlType(urlType);
        req.setSceneKey("E_PRESCRIPTION_AUTO_SIGN");
        return req;
    }
}
