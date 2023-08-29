package com.tencent.ess.api.flowmanage;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.ApproverInfo;
import com.tencentcloudapi.ess.v20201111.models.Component;
import com.tencentcloudapi.ess.v20201111.models.CreateFlowByFilesRequest;
import com.tencentcloudapi.ess.v20201111.models.CreateFlowByFilesResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

import static com.tencent.ess.config.Config.OperatorUserId;

public class CreateFlowByFilesApi {

    public static String CreateFlowByFiles(String operatorId, String flowName, ApproverInfo[] approvers, String fileId)
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

        //  请根据实际情况构造Components
        // request.setComponents(packComponents());

        CreateFlowByFilesResponse response = client.CreateFlowByFiles(request);

        return response.getFlowId();
    }

    /**
     * CreateFlowByFiles接口的详细参数使用样例，前面简要调用的场景不同，此版本旨在提供可选参数的填入参考。
     * 如果您在实现基础场景外有进一步的功能实现需求，可以参考此处代码。
     * 注意事项：此处填入参数仅为样例，请在使用时更换为实际值。
     */
    public static void CreateFlowByFilesExtended() throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreateFlowByFilesRequest request = new CreateFlowByFilesRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(OperatorUserId);
        request.setOperator(userInfo);

        request.setFlowName("测试合同");

        // 构建签署方信息
        // 注意：文件发起时，签署方不能进行控件填写！！！如果有填写需求，请设置为发起方填写，或者使用模板发起！！！

        // 企业静默签署
        ApproverInfo serverSignApprover = new ApproverInfo();
        // 这里可以设置企业方自动签章，分别可以使用坐标、表单域、关键字进行定位
        serverSignApprover.setSignComponents(new Component[]{
                        // 坐标定位，印章类型，并传入印章Id
                        BuildComponentNormal("SIGN_SEAL", "*************"),
                        // 表单域定位，印章类型，并传入印章Id
                        BuildComponentField("SIGN_SEAL", "*************"),
                        // 关键字定位，印章类型，并传入印章Id
                        BuildComponentKeyword("SIGN_SEAL", "*************")
                }
        );

        // 个人签署
        ApproverInfo approverInfo = new ApproverInfo();
        approverInfo.setApproverName("张三");
        approverInfo.setApproverMobile("1*********8");
        approverInfo.setSignComponents(new Component[]{
                        // 坐标定位，手写签名类型
                        BuildComponentNormal("SIGN_SIGNATURE", ""),
                        // 表单域定位，手写签名类型
                        BuildComponentField("SIGN_SIGNATURE", ""),
                        // 关键字定位，手写签名类型
                        BuildComponentKeyword("SIGN_SIGNATURE", "")
                }
        );

        // 企业签署
        ApproverInfo organizationApprover = new ApproverInfo();
        organizationApprover.setApproverName("李四");
        organizationApprover.setApproverMobile("1*********1");
        organizationApprover.setOrganizationName("XXXXX公司");
        // 这里可以设置企业手动签章（如果需要自动请使用静默签署），分别可以使用坐标、表单域、关键字进行定位
        organizationApprover.setSignComponents(new Component[]{
                        // 坐标定位，印章类型
                        BuildComponentNormal("SIGN_SEAL", ""),
                        // 表单域定位，印章类型
                        BuildComponentField("SIGN_SEAL", ""),
                        // 关键字定位，印章类型
                        BuildComponentKeyword("SIGN_SEAL", "")
                }
        );

        request.setApprovers(new ApproverInfo[]{serverSignApprover, approverInfo, organizationApprover});

        request.setFileIds(new String[]{"*************"});

        request.setFlowType("销售合同");

        // 经办人内容控件配置，必须在此处给控件进行赋值，合同发起时控件即被填写完成。
        request.setComponents(new Component[]{
                // 坐标定位，单行文本类型
                BuildComponentNormal("TEXT", "单行文本"),
                // 表单域定位，单行文本类型
                BuildComponentField("TEXT", "单行文本"),
                // 关键字定位，单行文本类型
                BuildComponentKeyword("TEXT", "单行文本"),
        });

        request.setNeedPreview(false);

        request.setPreviewType(0L);

        request.setDeadline(System.currentTimeMillis() / 1000 + 7 * 24 * 60 * 60);

        request.setUnordered(false);

        request.setNeedSignReview(false);

        request.setUserData("UserData");

        CreateFlowByFilesResponse response = client.CreateFlowByFiles(request);
        System.out.println(new Gson().toJson(response));
    }

    /**
     * 使用坐标模式进行控件定位
     */
    public static Component BuildComponentNormal(String componentType, String componentValue) {
        Component component = new Component();
        component.setComponentPosX(146.15625f);
        component.setComponentPosY(472.78125f);
        component.setComponentWidth(112f);
        component.setComponentHeight(40f);

        component.setFileIndex(0L);

        component.setComponentPage(1L);

        component.setComponentType(componentType);

        if (componentValue != "") {
            component.setComponentValue(componentValue);
        }
        return component;
    }

    /**
     * 使用关键字模式进行控件定位
     */
    public static Component BuildComponentKeyword(String componentType, String componentValue) {
        Component component = new Component();

        component.setGenerateMode("KEYWORD");

        component.setComponentId("签名");

        component.setComponentWidth(112f);
        component.setComponentHeight(40f);

        component.setFileIndex(0L);

        component.setOffsetX(10.5f);
        component.setOffsetY(10.5f);

        component.setKeywordOrder("Reverse");

        component.setKeywordIndexes(new Long[]{1L});

        component.setComponentType(componentType);

        if (componentValue != "") {
            component.setComponentValue(componentValue);
        }
        return component;
    }

    /**
     * 使用表单域模式进行控件定位
     */
    public static Component BuildComponentField(String componentType, String componentValue) {
        Component component = new Component();

        component.setGenerateMode("FIELD");

        component.setComponentId("表单");

        component.setFileIndex(0L);

        component.setComponentType(componentType);

        if (componentValue != "") {
            component.setComponentValue(componentValue);
        }
        return component;
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            String flowName = "****************";
            String fileId = "****************";


            // 签署参与者信息
            // 个人签署方
            ApproverInfo approver = new ApproverInfo();
            approver.setApproverType(1L);
            approver.setApproverName("****************");
            approver.setApproverMobile("****************");
            approver.setNotifyType("sms");

            // 签署人对应的签署控件
            Component component = new Component();
            component.setComponentPosX(417.15625F);
            component.setComponentPosY(497.671875F);
            component.setComponentWidth(74F);
            component.setComponentHeight(70F);
            component.setFileIndex(0L);
            component.setComponentPage(1L);
            component.setComponentType("SIGN_SIGNATURE");

            approver.setSignComponents(new Component[]{component});
            ApproverInfo[] approvers = new ApproverInfo[]{approver};

            String flowId = CreateFlowByFilesApi.CreateFlowByFiles(OperatorUserId, flowName, approvers, fileId);
            System.out.println(flowId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

