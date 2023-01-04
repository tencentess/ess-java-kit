package com.tencent.ess.api.flowmanage;

import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.CreatePrepareFlowRequest;
import com.tencentcloudapi.ess.v20201111.models.CreatePrepareFlowResponse;
import com.tencentcloudapi.ess.v20201111.models.FlowCreateApprover;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;

/**
 * 创建快速发起流程
 *
 * 官网文档：https://cloud.tencent.com/document/product/1323/83412
 *
 * 适用场景：用户通过API 合同文件及签署信息，并可通过我们返回的URL在页面完成签署控件等信息的编辑与确认，快速发起合同.
 * 注：该接口文件的resourceId 是通过上传文件之后获取的。
 */
public class CreatePrepareFlowApi {
    /**
     * 创建快速发起流程
     *
     * @param operatorId 经办人id
     * @param flowName   流程名
     * @param approvers  签署流程参与者信息
     * @param resourceId 资源Id,通过上传uploadFiles接口获得
     */
    public static CreatePrepareFlowResponse CreatePrepareFlow(String operatorId, String flowName, String resourceId, FlowCreateApprover[] approvers)
            throws Exception {

        // 构造默认的api客户端调用实例
        EssClient client = Client.getEssClient();

        // 构造请求体
        CreatePrepareFlowRequest request = new CreatePrepareFlowRequest();

        // 调用方用户信息，参考通用结构
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(operatorId);
        request.setOperator(userInfo);

        // 签署流程名称
        request.setFlowName(flowName);

        // 签署流程参与者信息
        request.setApprovers(approvers);

        // 资源Id,通过上传UploadFiles接口获得
        request.setResourceId(resourceId);

        return client.CreatePrepareFlow(request);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            String flowName = "****************";
            String resourceId = "yDRI5UUgygsupv5oUuO4zjEESmE4Ip0s";

            // FlowCreateApprover 入参参考 https://cloud.tencent.com/document/api/1323/70369#FlowCreateApprover
            // 参与者类型：
            //0：企业
            //1：个人
            //3：企业静默签署
            //注：类型为3（企业静默签署）时，此接口会默认完成该签署方的签署。
            FlowCreateApprover personInfo = new FlowCreateApprover();
            personInfo.setApproverType(1L);
            personInfo.setApproverName("李金泽");
            personInfo.setApproverMobile("15972212814");

            FlowCreateApprover[] approvers = new FlowCreateApprover[]{personInfo};

            CreatePrepareFlowResponse resp = CreatePrepareFlowApi.CreatePrepareFlow(Config.OperatorUserId, flowName, resourceId, approvers);
            System.out.println("url: " + resp.getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
