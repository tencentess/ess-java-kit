package com.tencent.ess.api.flowmanage;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CreateFlowRequest;
import com.tencentcloudapi.ess.v20201111.models.CreateFlowResponse;
import com.tencentcloudapi.ess.v20201111.models.FlowCreateApprover;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

import static com.tencent.ess.config.Config.OperatorUserId;

public class CreateFlowApi {

    public static String CreateFlow(String operatorId, String flowName, FlowCreateApprover[] approvers, boolean isAutoSign)
            throws Exception {

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreateFlowRequest request = new CreateFlowRequest();

        if (isAutoSign) {
            request.setAutoSignScene("E_PRESCRIPTION_AUTO_SIGN");
        }

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        request.setFlowName(flowName);
        request.setApprovers(approvers);

        CreateFlowResponse resp = client.CreateFlow(request);

        if (resp.getFlowId() == null || resp.getFlowId().length() == 0) {
            throw new Exception();
        }

        return resp.getFlowId();
    }

    /**
     * CreateFlow接口的详细参数使用样例，前面简要调用的场景不同，此版本旨在提供可选参数的填入参考
     * 如果您在实现基础场景外有进一步的功能实现需求，可以参考此处代码。
     * 注意事项：此处填入参数仅为样例，请在使用时更换为实际值。
     */
    public static void CreateFlowExtended() throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreateFlowRequest request = new CreateFlowRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(OperatorUserId);
        request.setOperator(userInfo);

        request.setFlowName("测试合同");

        // 构建签署方信息，注意这里的签署人类型、数量、顺序需要和模板中的设置保持一致

        // 企业静默签署
        FlowCreateApprover serverSignApprover = new FlowCreateApprover();
        serverSignApprover.setApproverType(3L);

        // 企业签署
        FlowCreateApprover organizationApprover = new FlowCreateApprover();
        organizationApprover.setApproverType(0L);
        organizationApprover.setApproverName("李四");
        organizationApprover.setApproverMobile("15987654321");
        organizationApprover.setOrganizationName("XXXXX公司");
        organizationApprover.setNotifyType("sms");

        // 个人签署
        FlowCreateApprover approverInfo = new FlowCreateApprover();
        approverInfo.setApproverType(1L);
        approverInfo.setApproverName("张三");
        approverInfo.setApproverMobile("15912345678");
        approverInfo.setNotifyType("sms");

        request.setApprovers(new FlowCreateApprover[]{
                serverSignApprover, organizationApprover, approverInfo
        });

        request.setUnordered(false);
        request.setUserData("UserData");
        request.setDeadLine(System.currentTimeMillis() / 1000 + 7 * 24 * 60 * 60);

        CreateFlowResponse response = client.CreateFlow(request);
        System.out.println(new Gson().toJson(response));
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            String flowName = "****************";

            FlowCreateApprover personInfo = new FlowCreateApprover();
            personInfo.setApproverType(1L);//1：个人
            personInfo.setApproverName("****************");
            personInfo.setApproverMobile("****************");

            FlowCreateApprover[] approvers = new FlowCreateApprover[]{personInfo};

            String flowId = CreateFlowApi.CreateFlow(OperatorUserId, flowName, approvers, false);
            System.out.println("flowId: " + flowId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

