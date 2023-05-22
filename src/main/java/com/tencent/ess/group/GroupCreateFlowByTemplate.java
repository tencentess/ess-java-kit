package com.tencent.ess.group;

import com.google.gson.Gson;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.*;

import java.util.concurrent.TimeUnit;

/**
 * 通过模板发起合同流程
 * <p>
 * 官网文档：https://cloud.tencent.com/document/api/1323/70361
 * https://cloud.tencent.com/document/api/1323/70364
 * https://cloud.tencent.com/document/api/1323/70357
 * <p>
 * 适用场景：在标准制式的合同场景中，可通过提前预制好模板文件，每次调用模板文件的id，补充合同内容信息及签署信息生成电子合同。
 */
public class GroupCreateFlowByTemplate {

    public static CreateFlowResponse CreateFlow(String operatorId, String proxyOrganizationId, String flowName, FlowCreateApprover[] approvers)
            throws TencentCloudSDKException {

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreateFlowRequest request = new CreateFlowRequest();

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

        // 签署流程参与者信息
        request.setApprovers(approvers);

        return client.CreateFlow(request);
    }

    public static CreateDocumentResponse CreateDocument(String operatorId,String proxyOrganizationId, String flowId, String templateId, String fileName)
            throws TencentCloudSDKException {

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();
        CreateDocumentRequest request = new CreateDocumentRequest();

        // 设置集团子企业账号
        Agent agent = new Agent();
        agent.setProxyOrganizationId(proxyOrganizationId);
        request.setAgent(agent);

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 流程id
        request.setFlowId(flowId);

        // 用户上传的模板ID,在控制台模版管理中可以找到
        // 单个个人签署模版
        request.setTemplateId(templateId);

        // 文件名列表,单个文件名最大长度200个字符
        String[] fileNames = new String[]{fileName};
        request.setFileNames(fileNames);

        return client.CreateDocument(request);
    }

    public static StartFlowResponse StartFlow(String operatorId, String proxyOrganizationId,String flowId) throws TencentCloudSDKException {
        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        StartFlowRequest request = new StartFlowRequest();

        // 设置集团子企业账号
        Agent agent = new Agent();
        agent.setProxyOrganizationId(proxyOrganizationId);
        request.setAgent(agent);

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 签署流程编号，由CreateFlow接口返回
        request.setFlowId(flowId);

        return client.StartFlow(request);
    }


    /**
     * 主企业代子企业使用模板发起合同的简单样例，如需更详细的参数使用说明，请参考 flowmanage 目录下的 CreateFlowApi/CreateDocumentApi/StartFlowApi
     * 注意：使用集团代发起功能，需要主企业和子企业均已加入集团，并且主企业OperatorUserId对应人员被赋予了对应操作权限
     */
    public static void main(String[] args) {
        try {
            // 需要代发起的子企业的企业id
            String proxyOrganizationId = "*****************";
            // 子企业模板id，需要在控制台查询获取。请勿使用主企业的模板id!
            String templateId = "**************";
            // 合同名称
            String flowName = "****************";
            // 姓名
            String name = "*****************";
            // 手机号
            String mobile = "*****************";
            // 文件名
            String fileName = "****************";

            // FlowCreateApprover https://cloud.tencent.com/document/api/1323/70369#FlowCreateApprover
            // 参与者类型：
            //0：企业
            //1：个人
            //3：企业静默签署
            //注：类型为3（企业静默签署）时，此接口会默认完成该签署方的签署。
            FlowCreateApprover personInfo = new FlowCreateApprover();
            personInfo.setApproverType(1L);//1：个人
            personInfo.setApproverName(name);
            personInfo.setApproverMobile(mobile);
            FlowCreateApprover[] approvers = new FlowCreateApprover[]{personInfo};

            // 1、调用CreateFlow接口创建流程
            CreateFlowResponse createFlowResponse = CreateFlow(Config.OperatorUserId, proxyOrganizationId,flowName, approvers);
            String flowId = createFlowResponse.getFlowId();
            System.out.println(new Gson().toJson(createFlowResponse));

            // 2、调用CreateDocument接口创建文档
            CreateDocumentResponse createDocumentResponse = CreateDocument(Config.OperatorUserId, proxyOrganizationId, flowId, templateId, fileName );
            System.out.println(new Gson().toJson(createDocumentResponse));

            TimeUnit.SECONDS.sleep(3);

            // 3、调用StartFlow接口开启流程
            StartFlowResponse startFlowResponse = StartFlow(Config.OperatorUserId, proxyOrganizationId,flowId);
            System.out.println(new Gson().toJson(startFlowResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
