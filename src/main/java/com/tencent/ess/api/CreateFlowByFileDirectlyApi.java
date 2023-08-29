package com.tencent.ess.api;

import com.tencent.ess.api.fileuploaddownload.UploadFilesApi;
import com.tencent.ess.api.flowmanage.CreateFlowByFilesApi;
import com.tencent.ess.api.flowmanage.CreateSchemeUrlApi;
import com.tencentcloudapi.ess.v20201111.models.ApproverInfo;
import com.tencentcloudapi.ess.v20201111.models.CreateSchemeUrlResponse;

public class CreateFlowByFileDirectlyApi {

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
