package com.tencent.ess.autosign;

import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.*;

public class CreatePreparedPersonalEsign {

    public static void main(String[] args) {
        CreatePreparedPersonalEsignRequest req = prepareCreatePreparedPersonalEsignRequest();
        // 身份证号码
        req.setIdCardNumber("110*************234");
        req.setIdCardType("ID_CARD");
        req.setUserName("用户的名称");
        req.setSealName("印章的名称");
        // 印章图片的base64,如下测试印章内容是 '王弓'
        req.setSealImage("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");

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        try {
            CreatePreparedPersonalEsignResponse res = client.CreatePreparedPersonalEsign(req);
            assert res != null;
            System.out.println(CreatePreparedPersonalEsignResponse.toJsonString(res));
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构造请求基本参数
     */
    public static CreatePreparedPersonalEsignRequest prepareCreatePreparedPersonalEsignRequest() {

        CreatePreparedPersonalEsignRequest req = new CreatePreparedPersonalEsignRequest();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(Config.OperatorUserId);
        req.setOperator(userInfo);
        return req;
    }
}
