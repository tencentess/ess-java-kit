package com.tencent.ess.api;

import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CreateFlowRequest;
import com.tencentcloudapi.ess.v20201111.models.CreateFlowResponse;
import com.tencentcloudapi.ess.v20201111.models.FlowCreateApprover;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

/**
 * 创建签署流程
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

        CreateFlowResponse resp = client.CreateFlow(request);

        if (resp.getFlowId() == null || resp.getFlowId().length() == 0) {
            throw new Exception();
        }

        return resp.getFlowId();
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

            String flowId = CreateFlowApi.CreateFlow(Config.OperatorUserId, flowName, approvers);
            System.out.println("flowId: " + flowId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

