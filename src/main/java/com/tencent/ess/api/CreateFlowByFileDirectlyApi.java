package com.tencent.ess.api;

import com.tencentcloudapi.ess.v20201111.models.ApproverInfo;
import com.tencentcloudapi.ess.v20201111.models.CreateSchemeUrlResponse;

/**
 * 通过文件路径直接发起签署流程，返回flowid
 */
public class CreateFlowByFileDirectlyApi {
    /**
     * 一键使用文件发起流程
     *
     * @param operatorId    经办人id
     * @param flowName      流程名
     * @param filePath      文件路径
     * @param approverInfos 签署者列表ApproverInfo[]
     * @return flowId、文件url组成的字符串数组
     */
    public static String[] CreateFlowByFileDirectly(String operatorId, String flowName, ApproverInfo[] approverInfos,
                                                    String filePath) throws Exception {
        // 1、上传文件获取fileId
        String fileId = UploadFilesApi.UploadFile(operatorId, filePath);

        // 2、创建签署流程
        String flowId = CreateFlowByFilesApi.CreateFlowByFiles(operatorId, flowName, approverInfos, fileId);

        // 3、获取签署链接
        CreateSchemeUrlResponse response = CreateSchemeUrlApi.CreateSchemeUrl(operatorId, flowId);

        return new String[]{flowId, response.getSchemeUrl()};
    }
}
