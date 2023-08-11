package com.tencent.ess.autosign;

import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CancelUserAutoSignEnableUrlRequest;
import com.tencentcloudapi.ess.v20201111.models.CancelUserAutoSignEnableUrlResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;
import com.tencentcloudapi.ess.v20201111.models.UserThreeFactor;

/**
 * 撤销自动签开通链接
 * <p>
 * 文档地址：https://qian.tencent.com/developers/companyApis/users/CancelUserAutoSignEnableUrl
 * <p>
 * 企业方可以通过此接口来撤销发送给个人用户的自动签开通链接，撤销后对应的个人用户开通链接失效。若个人用户已经完成开通，将无法撤销。（处方单场景专用，使用此接口请与客户经理确认）
 */
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