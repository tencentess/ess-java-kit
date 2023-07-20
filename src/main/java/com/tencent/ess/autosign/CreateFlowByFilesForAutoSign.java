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
 *
 * 文档地址：https://qian.tencent.com/developers/companyApis/startFlows/CreateFlowByFiles
 *
 * 此接口（CreateFlowByFiles）用来通过上传后的pdf资源编号来创建待签署的合同流程。
 * 适用场景1：适用非制式的合同文件签署。一般开发者自己有完整的签署文件，可以通过该接口传入完整的PDF文件及流程信息生成待签署的合同流程。
 * 适用场景2：可通过该接口传入制式合同文件，同时在指定位置添加签署控件。可以起到接口创建临时模板的效果。如果是标准的制式文件，建议使用模板功能生成模板ID进行合同流程的生成。
 * 注意事项：该接口需要依赖“多文件上传”接口生成pdf资源编号（FileIds）进行使用。
 *
 */
public class CreateFlowByFilesForAutoSign {

    /**
     * CreateFlowByFilesForAutoSign
     */
    public static void main(String[] args) throws Exception {

        // Step 1
        // 定义文件所在的路径
        String inputFilePath = "src/main/resources/medical.pdf";
        // 定义合同名
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


    /**
     * 一键使用文件发起流程
     *
     * @param operatorId    经办人id
     * @param flowName      流程名
     * @param filePath      文件路径
     * @param approverInfos 签署者列表ApproverInfo[]
     * @return flowId、文件url组成的字符串数组
     */
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

    /**
     * 通过上传后的pdf资源编号来创建待签署的合同流程
     *
     * @param operatorId 经办人id
     * @param flowName   流程名
     * @param approvers  签署流程参与者信息
     * @param fileId     pdf文件id
     * @return 流程id即flowId
     */
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

        // 签署流程名称,最大长度200个字符
        request.setFlowName(flowName);
        request.setApprovers(approvers);

        // 签署pdf文件的资源编号列表，通过UploadFiles接口获取
        request.setFileIds(new String[]{fileId});
        request.setAutoSignScene("E_PRESCRIPTION_AUTO_SIGN");
        request.setUnordered(true);

        //  请根据实际情况构造Components
        // request.setComponents(packComponents());

        // 发起方企业的签署人进行签署操作是否需要企业内部审批。
        // 若设置为true,审核结果需通过接口 CreateFlowSignReview 通知电子签，审核通过后，发起方企业签署人方可进行签署操作，否则会阻塞其签署操作。
        // 注：企业可以通过此功能与企业内部的审批流程进行关联，支持手动、静默签署合同。
        // request.setNeedSignReview(true);

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
        // 0：企业
        // 1：个人
        // 3：企业静默签署
        // 注：类型为3（企业静默签署）时，此接口会默认完成该签署方的签署。
        // 7: 个人自动签署，!!!!需要开通自动签后使用!!!!见CreatePreparedPersonalEsign接口
        approverInfo.setApproverType(7L);
        // 本环节需要操作人的名字
        approverInfo.setApproverName(name);
        // 本环节需要操作人的手机号
        approverInfo.setApproverMobile(mobile);
        // 身份证
        approverInfo.setApproverIdCardType("ID_CARD");
        approverInfo.setApproverIdCardNumber(idCardNum);
        // 签署人对应的签署控件
        Component component = BuildKeyWordComponent(keyWord, "Right", offSetX, offsetY, width, height);
        // 本环节操作人签署控件配置，为企业静默签署时，只允许类型为SIGN_SEAL（印章）和SIGN_DATE（日期）控件，并且传入印章编号
        approverInfo.setSignComponents(new Component[]{component});
        return approverInfo;
    }

    /**
     * 医疗自动签署-关键字-构建控件信息
     *
     * @param componentId      控件ID
     * @param relativeLocation 相对位置
     * @param componentWidth   控件宽度，单位pt
     * @param componentHeight  件高度，单位pt
     * @return 控件
     */
    public static Component BuildKeyWordComponent(String componentId,  String relativeLocation,
                                                  float offSetX, float offSetY, float componentWidth,
                                                  float componentHeight) {
        // 模板控件信息
        // 签署人对应的签署控件
        Component component = new Component();

        // ComponentId 关键字
        component.setComponentId(componentId);

        // 如果是 Component 控件类型，则可选类型为：
        // TEXT - 内容文本控件
        // DATE - 内容日期控件
        // CHECK_BOX - 勾选框控件
        // 如果是 SignComponent 控件类型，则可选类型为：
        // SIGN_SEAL - 签署印章控件
        // SIGN_DATE - 签署日期控件
        // SIGN_SIGNATURE - 手写签名控件
        component.setComponentType("SIGN_SIGNATURE");
        // 参数控件宽度，单位pt
        component.setComponentWidth(componentWidth);
        // 参数控件高度，单位pt
        component.setComponentHeight(componentHeight);
        // 控件所属文件的序号（取值为：0-N）
        component.setFileIndex(0L);
        // 参数控件所在页码，取值为：1-N
        component.setComponentPage(1L);
        // KEYWORD
        component.setGenerateMode("KEYWORD");
        // OffsetX: 20
        component.setOffsetX(offSetX);
        // OffsetY: -30
        component.setOffsetY(offSetY);
        // RelativeLocation: Right
        component.setRelativeLocation(relativeLocation);

        return component;
    }
}