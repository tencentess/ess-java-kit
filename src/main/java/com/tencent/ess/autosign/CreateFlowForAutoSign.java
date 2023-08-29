package com.tencent.ess.autosign;

import com.tencent.ess.api.CreateFlowByTemplateIdDirectlyApi;
import com.tencent.ess.api.fileuploaddownload.DescribeFileUrlsApi;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.ess.v20201111.models.*;


public class CreateFlowForAutoSign {

    // 构造签署人 - 以B2CC为例, 企业（静默签）-医生（个人自动签）-药师（个人自动签），实际请根据自己的场景构造签署方、控件
    private static FlowCreateApprover[] BuildFlowCreateApprovers() {
        // 发起方企业静默签署，此处需要在Config.java中设置一个持有的印章值 serverSignSealId
        FlowCreateApprover serverSignApprover = BuildServerSignFlowCreateApprover();

        // 个人签署方 医生 注意修改为真实的姓名、手机号、证件号
        FlowCreateApprover personApprover1 = BuildAutoSignPersonFlowCreateApprover(
                "医生姓名", "医生手机号", "医生身份证号");
        // 个人签署方 药师 注意修改为真实的姓名、手机号、证件号
        FlowCreateApprover personApprover2 = BuildAutoSignPersonFlowCreateApprover(
                "药师姓名", "药师手机号", "药师身份证号");

        return new FlowCreateApprover[]{serverSignApprover, personApprover1, personApprover2};
    }

    public static void main(String[] args) {

        try {
            // Step 1
            // 定义合同名
            String flowName = "自建应用-医疗签署合同" + System.currentTimeMillis();
            String operatorId = Config.OperatorUserId;
            String templateId = Config.TemplateId;

            // 构造签署人 - 以B2CC为例, 企业（静默签）-医生（个人自动签）-药师（个人自动签），实际请根据自己的场景构造签署方、控件
            // 注意修改姓名、手机号、身份证号
            FlowCreateApprover[] approvers = BuildFlowCreateApprovers();

            // Step 2
            // 发起合同 注意这里 isAutoSign为true，对应的 AutoSignScene为E_PRESCRIPTION_AUTO_SIGN
            String[] resp = CreateFlowByTemplateIdDirectlyApi.CreateFlowByTemplateIdDirectly(operatorId,
                    templateId, flowName, approvers, true);
            System.out.println("您创建的合同id为: ");
            System.out.println(resp[0]);

            // Step 3
            // 下载合同
            String url = DescribeFileUrlsApi.DescribeFileUrls(operatorId, resp[0]);
            // 返回合同下载链接
            System.out.println("请访问以下地址下载您的合同：");
            System.out.println(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 打包个人签署方参与者信息 ApproverType 7：个人自动签
    private static FlowCreateApprover BuildAutoSignPersonFlowCreateApprover(String name,
                                                                            String mobile,
                                                                            String idCardNumber) {
        // 签署参与者信息
        // 个人签署方
        FlowCreateApprover approverInfo = new FlowCreateApprover();

        approverInfo.setApproverType(7L);

        approverInfo.setApproverName(name);

        approverInfo.setApproverMobile(mobile);

        approverInfo.setApproverIdCardType("ID_CARD");
        approverInfo.setApproverIdCardNumber(idCardNumber);

        approverInfo.setNotifyType("NONE");

        return approverInfo;
    }

    // 打包企业静默签署方参与者信息
    private static FlowCreateApprover BuildServerSignFlowCreateApprover() {
        // 签署参与者信息
        // 企业签署方
        FlowCreateApprover serverSignApprover = new FlowCreateApprover();

        serverSignApprover.setApproverType(3L);

        return serverSignApprover;
    }
}
