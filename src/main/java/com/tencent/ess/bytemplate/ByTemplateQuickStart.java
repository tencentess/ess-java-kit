package com.tencent.ess.bytemplate;

import com.tencent.ess.api.fileuploaddownload.DescribeFileUrlsApi;
import com.tencent.ess.api.CreateFlowByTemplateIdDirectlyApi;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.ess.v20201111.models.FlowCreateApprover;

/**
 * 使用模板发起合同QuickStart
 */
public class ByTemplateQuickStart {

    /**
     * ByFileQuickStart
     */
    public static void main(String[] args) throws Exception {
        // Step 1
        // 定义合同名
        String flowName = "我的第一个合同";
        String operatorId = Config.OperatorUserId;
        String templateId = Config.TemplateId;

        /// 构造签署人
        /// 此块代码中的$approvers仅用于快速发起一份合同样例
        String personApproverName = "****************"; // 个人签署方的姓名，必须是真实的才能正常签署
        String personApproverMobile = "****************"; // 个人签署方的手机号，必须是真实的才能正常签署
        FlowCreateApprover[] approvers = BuildPersonFlowCreateApprover(personApproverName, personApproverMobile);

        // 如果是正式接入，请参考入buildApprovers函数内查看说明，构造需要的场景参数
        // approvers = buildApprovers();

        // Step 2
        // 使用文件发起合同
        // 发起合同
        String[] resp = CreateFlowByTemplateIdDirectlyApi.CreateFlowByTemplateIdDirectly(operatorId, templateId, flowName, approvers);
        System.out.println("您创建的合同id为: ");
        System.out.println(resp[0]);
        // 返回签署的链接
        System.out.println("签署链接（请在手机浏览器中打开）为：");
        System.out.println(resp[1]);

        // Step 3
        // 下载合同
        String url = DescribeFileUrlsApi.DescribeFileUrls(operatorId, resp[0]);
        // 返回合同下载链接
        System.out.println("请访问以下地址下载您的合同：");
        System.out.println(url);
    }

    // 打包个人签署方参与者信息 ApproverType 1：个人
    private static FlowCreateApprover[] BuildPersonFlowCreateApprover(String name, String mobile) {

        // 签署参与者信息
        // 个人签署方
        FlowCreateApprover approverInfo = new FlowCreateApprover();
        // 0：企业
        // 1：个人
        // 3：企业静默签署
        // 注：类型为3（企业静默签署）时，此接口会默认完成该签署方的签署。
        approverInfo.setApproverType(1L);
        // 本环节需要操作人的名字
        approverInfo.setApproverName(name);
        // 本环节需要操作人的手机号
        approverInfo.setApproverMobile(mobile);
        // sms--短信，none--不通知
        approverInfo.setNotifyType("sms");

        return new FlowCreateApprover[]{approverInfo};
    }

    // 打包企业签署方参与者信息
    private static FlowCreateApprover[] BuildOrganizationFlowCreateApprover(String organizationName, String name, String mobile) {

        // 签署参与者信息
        // 企业签署方
        FlowCreateApprover organizationApprover = new FlowCreateApprover();
        // 0：企业
        // 1：个人
        // 3：企业静默签署
        // 注：类型为3（企业静默签署）时，此接口会默认完成该签署方的签署。
        organizationApprover.setApproverType(0L);
        // 本环节需要企业操作人的企业名称
        organizationApprover.setOrganizationName(organizationName);
        // 本环节需要操作人的名字
        organizationApprover.setApproverName(name);
        // 本环节需要操作人的手机号
        organizationApprover.setApproverMobile(mobile);
        // sms--短信，none--不通知
        organizationApprover.setNotifyType("none");

        return new FlowCreateApprover[]{organizationApprover};
    }

    // 打包企业静默签署方参与者信息
    private static FlowCreateApprover[] BuildServerSignFlowCreateApprover(String serverSignSealId) {
        // 签署参与者信息
        // 企业签署方
        FlowCreateApprover serverSignApprover = new FlowCreateApprover();
        // 0：企业
        // 1：个人
        // 3：企业静默签署
        // 注：类型为3（企业静默签署）时，此接口会默认完成该签署方的签署。
        serverSignApprover.setApproverType(3L);

        return new FlowCreateApprover[]{serverSignApprover};
    }

    // 构造签署人 - 以B2B2C为例, 实际请根据自己的场景构造签署方、控件
    private static FlowCreateApprover[] BuildFlowCreateApprovers() {
        // 发起方企业静默签署，此处需要在Config.java中设置一个持有的印章值 serverSignSealId
        FlowCreateApprover[] serverSignApprover = BuildServerSignFlowCreateApprover(Config.ServerSignSealId);

        // 另一家企业签署方
        String organizationName = "****************";
        String organizationUserName = "****************";
        String organizationUserMobile = "****************";
        FlowCreateApprover[] organizationApprover = BuildOrganizationFlowCreateApprover(organizationName,
                organizationUserName, organizationUserMobile);

        // 个人签署方
        String personApproverName = "****************";
        String personApproverMobile = "****************";
        FlowCreateApprover[] personApprover = BuildPersonFlowCreateApprover(personApproverName, personApproverMobile);

        return ConcatApproverInfos(serverSignApprover, organizationApprover, personApprover);
    }

    // 把多个签署者列表合并为一个签署者列表
    private static FlowCreateApprover[] ConcatApproverInfos(FlowCreateApprover[]... arrays) {
        int length = 0;
        for (FlowCreateApprover[] array : arrays) {
            length += array.length;
        }
        FlowCreateApprover[] result = new FlowCreateApprover[length];
        int pos = 0;
        for (FlowCreateApprover[] array : arrays) {
            for (FlowCreateApprover element : array) {
                result[pos] = element;
                pos++;
            }
        }
        return result;
    }
}
