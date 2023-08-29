package com.tencent.ess.autosign;

import com.tencent.ess.api.fileuploaddownload.DescribeFileUrlsApi;
import com.tencent.ess.api.fileuploaddownload.UploadFilesApi;
import com.tencent.ess.api.flowmanage.CreateSchemeUrlApi;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.*;



/**
 * 使用文件发起合同QuickStart 医疗自动签专用 B2CC
 *
 * 关键字定位签署坐标（请根据实际PDF中的文件调整关键字）
 */
public class CreateFlowByFilesForAutoSign {

    /**
     * CreateFlowByFilesForAutoSign
     */
    public static void main(String[] args) throws Exception {

        // Step 1

        String inputFilePath = "src/main/resources/medical.pdf";

        String flowName = "自动签署测试合同-"+System.currentTimeMillis();
        String operatorId = Config.OperatorUserId;

        /// 构造自动签签署人
        /// 此块代码中的$approvers仅用于快速发起一份合同样例，非正式对接用
        // 医生的信息
        String personApproverName = "xx"; // 个人签署方的姓名，必须是真实的才能正常签署
        String personApproverMobile = "xx"; // 个人签署方的手机号，必须是真实的才能正常签署
        String idCardNumber = "xx"; // 身份证号
        ApproverInfo approversForDoctor = BuildAutoSignPersonApprover(personApproverName, personApproverMobile,
                idCardNumber, "医生", 20F, -30F, 100F, 100L);
        // 药师的信息
        String personApproverNameForMedical = "xx"; // 个人签署方的姓名，必须是真实的才能正常签署
        String personApproverMobileForMedical = "xx"; // 个人签署方的手机号，必须是真实的才能正常签署
        String idCardNumberForMedical = "xx"; // 身份证号
        ApproverInfo approversForMedical = BuildAutoSignPersonApprover(personApproverNameForMedical,
                personApproverMobileForMedical, idCardNumberForMedical,
                "药师", 20F, -30F, 100F, 100L);

        ApproverInfo[] allApprovers = new ApproverInfo[]{approversForDoctor, approversForMedical};

        // Step 2
        // 使用文件发起合同
        // 发起合同
        String[] resp = AutoSignCreateFlowByFileDirectly(operatorId, flowName, allApprovers, inputFilePath);
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

    public static String[] AutoSignCreateFlowByFileDirectly(String operatorId, String flowName,
                                                            ApproverInfo[] approverInfos,
                                                            String filePath) throws Exception {
        // 1、上传文件获取fileId
        String fileId = UploadFilesApi.UploadFile(operatorId, filePath);

        // 2、创建签署流程
        String flowId = AutoSignCreateFlowByFiles(operatorId, flowName, approverInfos, fileId);

        // 3、获取签署链接
        CreateSchemeUrlResponse response = CreateSchemeUrlApi.CreateSchemeUrl(operatorId, flowId);

        return new String[]{flowId, response.getSchemeUrl()};
    }

    public static String AutoSignCreateFlowByFiles(String operatorId, String flowName,
                                                   ApproverInfo[] approvers, String fileId)
            throws TencentCloudSDKException {

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreateFlowByFilesRequest request = new CreateFlowByFilesRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        request.setFlowName(flowName);
        request.setApprovers(approvers);

        request.setFileIds(new String[]{fileId});
        request.setAutoSignScene("E_PRESCRIPTION_AUTO_SIGN");
        request.setUnordered(true);

        //  请根据实际情况构造Components
        // request.setComponents(packComponents());

        CreateFlowByFilesResponse response = client.CreateFlowByFiles(request);

        return response.getFlowId();
    }


    // 个人自动签署方参与者信息 ApproverType 1：个人 ApproverType 7：个人自动签署，需要开通自动签后使用
    private static ApproverInfo BuildAutoSignPersonApprover(String name, String mobile, String idCardNum,
                                                            String keyWord, float offSetX, float offsetY,
                                                            float width, float height) {
        // 签署参与者信息
        // 个人签署方
        ApproverInfo approverInfo = new ApproverInfo();
        approverInfo.setApproverType(7L);
        approverInfo.setApproverName(name);
        approverInfo.setApproverMobile(mobile);
        approverInfo.setApproverIdCardType("ID_CARD");
        approverInfo.setApproverIdCardNumber(idCardNum);

        Component component = BuildKeyWordComponent(keyWord, "Right", offSetX, offsetY, width, height);

        approverInfo.setSignComponents(new Component[]{component});
        return approverInfo;
    }

    public static Component BuildKeyWordComponent(String componentId,  String relativeLocation,
                                                  float offSetX, float offSetY, float componentWidth,
                                                  float componentHeight) {
        // 模板控件信息
        // 签署人对应的签署控件
        Component component = new Component();

        // ComponentId 关键字
        component.setComponentId(componentId);

        component.setComponentType("SIGN_SIGNATURE");

        component.setComponentWidth(componentWidth);

        component.setComponentHeight(componentHeight);

        component.setFileIndex(0L);

        component.setComponentPage(1L);

        component.setGenerateMode("KEYWORD");

        component.setOffsetX(offSetX);

        component.setOffsetY(offSetY);

        component.setRelativeLocation(relativeLocation);

        return component;
    }
}