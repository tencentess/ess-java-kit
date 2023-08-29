package com.tencent.ess.byfile;

import com.tencent.ess.api.fileuploaddownload.DescribeFileUrlsApi;
import com.tencent.ess.api.CreateFlowByFileDirectlyApi;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.ess.v20201111.models.ApproverInfo;
import com.tencentcloudapi.ess.v20201111.models.Component;

/**
 * 使用文件发起合同QuickStart
 */
public class ByFileQuickStart {

    /**
     * ByFileQuickStart
     */
    public static void main(String[] args) throws Exception {

        // Step 1
        // 定义文件所在的路径
        String inputFilePath = "src/main/resources/blank.pdf";
        // 定义合同名
        String flowName = "我的第一个合同";
        String operatorId = Config.OperatorUserId;

        /// 构造签署人
        /// 此块代码中的$approvers仅用于快速发起一份合同样例，非正式对接用
        String personApproverName = "****************"; // 个人签署方的姓名，必须是真实的才能正常签署
        String personApproverMobile = "****************"; // 个人签署方的手机号，必须是真实的才能正常签署
        ApproverInfo[] approvers = BuildPersonApprover(personApproverName, personApproverMobile);

        // 如果是正式接入，请参考入BuildApprovers函数内查看说明，构造需要的场景参数
        // approvers = BuildApprovers();

        // Step 2
        // 使用文件发起合同
        // 发起合同
        String[] resp = CreateFlowByFileDirectlyApi.CreateFlowByFileDirectly(operatorId, flowName, approvers, inputFilePath);
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
    private static ApproverInfo[] BuildPersonApprover(String name, String mobile) {

        // 签署参与者信息
        // 个人签署方
        ApproverInfo approverInfo = new ApproverInfo();
        approverInfo.setApproverType(1L);
        approverInfo.setApproverName(name);
        approverInfo.setApproverMobile(mobile);
        approverInfo.setNotifyType("sms");
        // 签署人对应的签署控件
        Component component = BuildComponent("SIGN_SIGNATURE", "", 417.15625F, 497.671875F, 74F, 70F, 0L, 1L);

        approverInfo.setSignComponents(new Component[]{component});

        return new ApproverInfo[]{approverInfo};
    }

    // 打包企业签署方参与者信息
    private static ApproverInfo[] BuildOrganizationApprover(String organizationName, String name, String mobile) {

        // 签署参与者信息
        // 企业签署方
        ApproverInfo organizationApprover = new ApproverInfo();
        organizationApprover.setApproverType(0L);
        organizationApprover.setOrganizationName(organizationName);
        organizationApprover.setApproverName(name);
        organizationApprover.setApproverMobile(mobile);
        organizationApprover.setNotifyType("none");

        Component component = BuildComponent("SIGN_SEAL", "", 120F, 120F, 74F, 70F, 0L, 1L);

        organizationApprover.setSignComponents(new Component[]{component});
        return new ApproverInfo[]{organizationApprover};
    }

    // 打包企业静默签署方参与者信息
    private static ApproverInfo[] BuildServerSignApprover(String serverSignSealId) {
        // 签署参与者信息
        // 企业签署方
        ApproverInfo serverSignApprover = new ApproverInfo();
        serverSignApprover.setApproverType(3L);

        Component component = BuildComponent("SIGN_SEAL", serverSignSealId, 200F, 200F, 74F, 70F, 0L, 1L);

        serverSignApprover.setSignComponents(new Component[]{component});
        return new ApproverInfo[]{serverSignApprover};
    }

    // 构造签署人 - 以B2B2C为例, 实际请根据自己的场景构造签署方、控件
    private static ApproverInfo[] BuildApprovers() {
        // 发起方企业静默签署，此处需要在Config.java中设置一个持有的印章值 serverSignSealId
        ApproverInfo[] serverSignApprover = BuildServerSignApprover(Config.ServerSignSealId);

        // 另一家企业签署方
        String organizationName = "****************";
        String organizationUserName = "****************";
        String organizationUserMobile = "****************";
        ApproverInfo[] organizationApprover = BuildOrganizationApprover(organizationName,
                organizationUserName, organizationUserMobile);

        // 个人签署方
        String personApproverName = "****************";
        String personApproverMobile = "****************";
        ApproverInfo[] personApprover = BuildPersonApprover(personApproverName, personApproverMobile);

        return ConcatApproverInfos(serverSignApprover, organizationApprover, personApprover);
    }

    // 把多个签署者列表合并为一个签署者列表
    private static ApproverInfo[] ConcatApproverInfos(ApproverInfo[]... arrays) {
        int length = 0;
        for (ApproverInfo[] array : arrays) {
            length += array.length;
        }
        ApproverInfo[] result = new ApproverInfo[length];
        int pos = 0;
        for (ApproverInfo[] array : arrays) {
            for (ApproverInfo element : array) {
                result[pos] = element;
                pos++;
            }
        }
        return result;
    }

    /**
     * 构建控件信息
     */
    public static Component BuildComponent(String componentType, String componentValue,
                                           float componentPosX, float componentPosY, float componentWidth, float componentHeight, long fileIndex, long componentPage) {
        // 模板控件信息
        // 签署人对应的签署控件
        Component component = new Component();

        component.setComponentType(componentType);
        component.setComponentValue(componentValue);
        component.setComponentPosX(componentPosX);
        component.setComponentPosY(componentPosY);
        component.setComponentWidth(componentWidth);
        component.setComponentHeight(componentHeight);
        component.setFileIndex(fileIndex);
        component.setComponentPage(componentPage);

        return component;
    }
}
