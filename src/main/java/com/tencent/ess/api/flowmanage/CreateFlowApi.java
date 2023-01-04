package com.tencent.ess.api.flowmanage;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CreateFlowRequest;
import com.tencentcloudapi.ess.v20201111.models.CreateFlowResponse;
import com.tencentcloudapi.ess.v20201111.models.FlowCreateApprover;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

import static com.tencent.ess.config.Config.OperatorUserId;

/**
 * 创建签署流程
 * <p>
 * 官网文档：https://cloud.tencent.com/document/api/1323/70361
 * <p>
 * 适用场景：在标准制式的合同场景中，可通过提前预制好模板文件，每次调用模板文件的id，补充合同内容信息及签署信息生成电子合同。
 * 注：该接口是通过模板生成合同流程的前置接口，先创建一个不包含签署文件的流程。配合“创建电子文档”接口和“发起流程”接口使用。
 */
public class CreateFlowApi {
    /**
     * 创建签署流程
     *
     * @param operatorId 经办人id
     * @param flowName   流程名
     * @param approvers  签署流程参与者信息
     * @return 流程id即flowId
     */
    public static String CreateFlow(String operatorId, String flowName, FlowCreateApprover[] approvers)
            throws Exception {

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreateFlowRequest request = new CreateFlowRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 签署流程名称,最大长度200个字符
        request.setFlowName(flowName);

        // 签署流程参与者信息
        request.setApprovers(approvers);

        // 发起方企业的签署人进行签署操作是否需要企业内部审批。
        // 若设置为true,审核结果需通过接口 CreateFlowSignReview 通知电子签，审核通过后，发起方企业签署人方可进行签署操作，否则会阻塞其签署操作。
        // 注：企业可以通过此功能与企业内部的审批流程进行关联，支持手动、静默签署合同。
        // request.setNeedSignReview(true);

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

        // 签署流程名称,最大长度200个字符
        request.setFlowName("测试合同");

        // 构建签署方信息，注意这里的签署人类型、数量、顺序需要和模板中的设置保持一致

        // 企业静默签署
        // 这里我们设置签署方类型为企业方静默签署3，注意当类型为静默签署时，签署人会默认设置为发起方经办人。此时姓名手机号企业名等信息无需填写，且填写无效
        // 企业静默签署不会发送短信通知
        FlowCreateApprover serverSignApprover = new FlowCreateApprover();
        serverSignApprover.setApproverType(3L);

        // 企业签署
        FlowCreateApprover organizationApprover = new FlowCreateApprover();
        organizationApprover.setApproverType(0L);
        // 本环节需要操作人的名字
        organizationApprover.setApproverName("李四");
        // 本环节需要操作人的手机号
        organizationApprover.setApproverMobile("15987654321");
        // 本环节需要企业操作人的企业名称，请注意此处的企业名称要是真实有效的，企业需要在电子签平台进行注册且签署人有加入该企业方能签署。
        // 注：如果该企业尚未注册，或者签署人尚未加入企业，合同仍然可以发起
        organizationApprover.setOrganizationName("XXXXX公司");
        // 合同发起后是否短信通知签署方进行签署：sms--短信，none--不通知
        organizationApprover.setNotifyType("sms");

        // 个人签署
        FlowCreateApprover approverInfo = new FlowCreateApprover();
        approverInfo.setApproverType(1L);
        // 本环节需要操作人的名字
        approverInfo.setApproverName("张三");
        // 本环节需要操作人的手机号
        approverInfo.setApproverMobile("15912345678");
        // 合同发起后是否短信通知签署方进行签署：sms--短信，none--不通知
        approverInfo.setNotifyType("sms");

        request.setApprovers(new FlowCreateApprover[]{
                serverSignApprover, organizationApprover, approverInfo
        });

        // 发送类型：
        //true：无序签
        //false：有序签
        //注：默认为false（有序签），请和模板中的配置保持一致。如果传值不一致会以模板中设置的为准！
        request.setUnordered(false);

        // 用户自定义字段，回调的时候会进行透传，长度需要小于20480
        request.setUserData("UserData");

        // 签署流程的签署截止时间。
        //值为unix时间戳,精确到秒,不传默认为当前时间一年后
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

            // FlowCreateApprover https://cloud.tencent.com/document/api/1323/70369#FlowCreateApprover
            // 参与者类型：
            //0：企业
            //1：个人
            //3：企业静默签署
            //注：类型为3（企业静默签署）时，此接口会默认完成该签署方的签署。
            FlowCreateApprover personInfo = new FlowCreateApprover();
            personInfo.setApproverType(1L);//1：个人
            personInfo.setApproverName("****************");
            personInfo.setApproverMobile("****************");

            FlowCreateApprover[] approvers = new FlowCreateApprover[]{personInfo};

            String flowId = CreateFlowApi.CreateFlow(OperatorUserId, flowName, approvers);
            System.out.println("flowId: " + flowId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

