package com.tencent.ess.autosign;

import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.*;

/**
 * 创建个人用户CA证书的证明图片
 * <p>
 * 文档地址：https://qian.tencent.com/developers/companyApis/users/CreatePersonAuthCertificateImage
 * <p>
 * 企业下的个人可以通过此接口创建个人用户CA证书的证明图片，图片上显示个人申请CA证书的申请时间信息
 */
public class CreatePersonAuthCertificateImage {

    public static void main(String[] args) {
        CreatePersonAuthCertificateImageRequest req = prepareCreatePersonAuthCertificateImageRequest();
        // 身份证号码
        req.setIdCardNumber("110120200010221234");
        req.setIdCardType("ID_CARD");
        req.setUserName("用户的名称");

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        try {
            CreatePersonAuthCertificateImageResponse res = client.CreatePersonAuthCertificateImage(req);
            assert res != null;
            System.out.println(CreatePersonAuthCertificateImageResponse.toJsonString(res));
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构造请求基本参数
     */
    public static CreatePersonAuthCertificateImageRequest prepareCreatePersonAuthCertificateImageRequest() {
        CreatePersonAuthCertificateImageRequest req = new CreatePersonAuthCertificateImageRequest();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(Config.OperatorUserId);
        req.setOperator(userInfo);
        return req;
    }
}
