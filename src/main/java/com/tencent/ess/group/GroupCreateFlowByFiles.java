package com.tencent.ess.group;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.*;

import static com.tencent.ess.config.Config.OperatorUserId;

/**
 * 通过上传后的pdf资源编号来创建待签署的合同流程
 * <p>
 * 官网文档：https://cloud.tencent.com/document/api/1323/70360
 * <p>
 * 适用场景1：适用非制式的合同文件签署。一般开发者自己有完整的签署文件，可以通过该接口传入完整的PDF文件及流程信息生成待签署的合同流程。
 * 适用场景2：可通过改接口传入制式合同文件，同时在指定位置添加签署控件。可以起到接口创建临时模板的效果。如果是标准的制式文件，建议使用模板功能生成模板ID进行合同流程的生成。
 * 注意事项：该接口需要依赖“多文件上传”接口生成pdf资源编号（FileIds）进行使用。
 */
public class GroupCreateFlowByFiles {
    /**
     * 通过上传后的pdf资源编号来创建待签署的合同流程
     *
     * @param operatorId          经办人id
     * @param proxyOrganizationId 代发起子企业的企业id
     * @param flowName            流程名
     * @param approvers           签署流程参与者信息
     * @param fileId              pdf文件id
     * @return 流程id即flowId
     */
    public static CreateFlowByFilesResponse CreateFlowByFiles(String operatorId, String proxyOrganizationId, String flowName, ApproverInfo[] approvers, String fileId)
            throws TencentCloudSDKException {

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreateFlowByFilesRequest request = new CreateFlowByFilesRequest();

        // 设置集团子企业账号
        Agent agent = new Agent();
        agent.setProxyOrganizationId(proxyOrganizationId);
        request.setAgent(agent);

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 签署流程名称,最大长度200个字符
        request.setFlowName(flowName);
        request.setApprovers(approvers);

        // 签署pdf文件的资源编号列表，通过UploadFiles接口获取
        request.setFileIds(new String[]{fileId});

        return client.CreateFlowByFiles(request);
    }


    /**
     * 主企业代子企业使用文件发起合同的简单样例，如需更详细的参数使用说明，请参考 flowmanage/CreateFlowByFilesApi
     * 注意：使用集团代发起功能，需要主企业和子企业均已加入集团，并且主企业OperatorUserId对应人员被赋予了对应操作权限
     */
    public static void main(String[] args) {
        try {
            // 需要代发起的子企业的企业id
            String proxyOrganizationId = "*****************";
            // 合同名
            String flowName = "*****************";
            // 文件id，注意这里是由子企业账户上传产生的。如果使用主企业的文件则会报错无权限！
            String fileId = "*****************";
            // 姓名
            String name = "*****************";
            // 手机号
            String mobile = "*****************";

            // 签署参与者信息
            // 个人签署方
            ApproverInfo approver = new ApproverInfo();
            // 参与者类型：
            // 0：企业
            // 1：个人
            // 3：企业静默签署
            // 注：类型为3（企业静默签署）时，此接口会默认完成该签署方的签署
            approver.setApproverType(1L);
            // 本环节需要操作人的名字
            approver.setApproverName(name);
            // 本环节需要操作人的手机号
            approver.setApproverMobile(mobile);
            // 发送短信
            approver.setNotifyType("sms");

            // 签署人对应的签署控件
            Component component = new Component();
            // 参数控件X位置，单位pt
            component.setComponentPosX(417.15625F);
            // 参数控件Y位置，单位pt
            component.setComponentPosY(497.671875F);
            // 参数控件宽度，单位pt
            component.setComponentWidth(74F);
            // 参数控件高度，单位pt
            component.setComponentHeight(70F);
            // 控件所属文件的序号（取值为：0-N）
            component.setFileIndex(0L);
            // 参数控件所在页码，取值为：1-N
            component.setComponentPage(1L);
            // 可选类型为：
            // SIGN_SEAL - 签署印章控件
            // SIGN_DATE - 签署日期控件
            // SIGN_SIGNATURE - 手写签名控件
            component.setComponentType("SIGN_SIGNATURE");

            approver.setSignComponents(new Component[]{component});
            ApproverInfo[] approvers = new ApproverInfo[]{approver};

            CreateFlowByFilesResponse response = CreateFlowByFiles(OperatorUserId, proxyOrganizationId, flowName, approvers, fileId);
            System.out.println(new Gson().toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

