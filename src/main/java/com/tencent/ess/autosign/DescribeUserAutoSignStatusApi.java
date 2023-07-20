package com.tencent.ess.autosign;

import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.DescribeUserAutoSignStatusRequest;
import com.tencentcloudapi.ess.v20201111.models.DescribeUserAutoSignStatusResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

/**
 * 查询个人用户开通自动签状态
 *
 * 文档地址：https://qian.tencent.com/developers/companyApis/users/DescribeUserAutoSignStatus
 *
 * 企业方可以通过此接口查询个人用户自动签开启状态。（处方单场景专用，使用此接口请与客户经理确认）
 *
 */
public class DescribeUserAutoSignStatusApi {

    public static void main(String[] args) {

        DescribeUserAutoSignStatusRequest req = prepareDescribeUserAutoSignStatusRequest();
        req.setUserInfo(CreateUserAutoSignEnableUrlApi.PrepareUserThreeFactor("姓名", "身份证号"));

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        try {
            DescribeUserAutoSignStatusResponse res = client.DescribeUserAutoSignStatus(req);
            assert res != null;
            System.out.println(DescribeUserAutoSignStatusResponse.toJsonString(res));
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构造请求基本参数
     */
    public static DescribeUserAutoSignStatusRequest prepareDescribeUserAutoSignStatusRequest() {

        DescribeUserAutoSignStatusRequest req = new DescribeUserAutoSignStatusRequest();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(Config.OperatorUserId);
        req.setOperator(userInfo);
        req.setSceneKey("E_PRESCRIPTION_AUTO_SIGN");
        return req;
    }
}
