package com.tencent.ess.autosign;

import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.*;

/**
 * 获取个人用户自动签开启链接
 *
 * 文档地址：https://qian.tencent.com/developers/companyApis/users/CreateUserAutoSignEnableUrl
 *
 * 企业方可以通过此接口获取个人用户开启自动签的跳转链接（处方单场景专用，使用此接口请与客户经理确认）
 *
 */
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

    /**
     * 构造用户信息
     * @param name 姓名
     * @param idCardNum 身份证号
     * @return threeFactor 用户信息
     */
    public static UserThreeFactor PrepareUserThreeFactor(String name, String idCardNum) {
        UserThreeFactor threeFactor = new UserThreeFactor();
        // 姓名
        threeFactor.setName(name);
        // 身份证号
        threeFactor.setIdCardNumber(idCardNum);
        threeFactor.setIdCardType("ID_CARD");
        return threeFactor;
    }

    /**
     * 构造自动签配置
     * @param threeFactor 用户身份信息
     * @param callBackUrl 回调地址
     * @param selfDefineSeal 是否允许自定义印章：true-允许
     * @param needSealImgCallback 是否需要回调印章：true-需要
     * @param needCertInfoCallback 是否需要签名证书回调：true-需要
     * @return autoSignConfig 自动签配置
     */
    public static AutoSignConfig PrepareUserAutoSignConfig(
            UserThreeFactor threeFactor,
            String callBackUrl,
            boolean selfDefineSeal,
            boolean needSealImgCallback,
            boolean needCertInfoCallback
            ) {
        AutoSignConfig autoSignConfig = new AutoSignConfig();
        autoSignConfig.setUserInfo(threeFactor);
        // 回调地址
        autoSignConfig.setCallbackUrl(callBackUrl);
        // 是否允许自定义印章：true-允许
        autoSignConfig.setUserDefineSeal(selfDefineSeal);
        // 是否需要回调印章：true-需要
        autoSignConfig.setSealImgCallback(needSealImgCallback);
        // 是否需要签名证书回调：true-需要
        autoSignConfig.setCertInfoCallback(needCertInfoCallback);
        return autoSignConfig;
    }

    /**
     * 构造请求基本参数
     * @param urlType 链接类型：默认-小程序，H5SIGN-h5端链接
     * @return CreateUserAutoSignEnableUrlRequest 请求体
     */
    public static CreateUserAutoSignEnableUrlRequest prepareCreateUserAutoSignEnableUrlRequest(String urlType) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(Config.OperatorUserId);

        CreateUserAutoSignEnableUrlRequest req = new CreateUserAutoSignEnableUrlRequest();

        req.setOperator(userInfo);
        // 链接类型：默认-小程序，H5SIGN-h5端链接
        req.setUrlType(urlType);
        req.setSceneKey("E_PRESCRIPTION_AUTO_SIGN");
        return req;
    }
}
