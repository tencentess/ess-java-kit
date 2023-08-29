package com.tencent.ess.api.fileuploaddownload;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.EssClient;
import com.tencentcloudapi.ess.v20201111.models.Caller;
import com.tencentcloudapi.ess.v20201111.models.UploadFile;
import com.tencentcloudapi.ess.v20201111.models.UploadFilesRequest;
import com.tencentcloudapi.ess.v20201111.models.UploadFilesResponse;
import com.tencent.ess.common.Client;
import com.tencent.ess.config.Config;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class UploadFilesApi {

    public static String UploadFile(String operatorId, String filePath)
            throws TencentCloudSDKException, IOException {

        String fileBase64 = getBase64FileBody(filePath);
        File file = new File(filePath);
        String fileName = file.getName();
        return UploadFile(operatorId, fileName, fileBase64);
    }

    public static String UploadFile(String operatorId, String fileName, byte[] fileBytes)
            throws TencentCloudSDKException {

        Base64.Encoder encoder = Base64.getEncoder();
        String fileBase64 = encoder.encodeToString(fileBytes);
        return UploadFile(operatorId, fileName, fileBase64);
    }

    public static String UploadFile(String operatorId, String fileName, String fileBase64)
            throws TencentCloudSDKException {

        // 构造客户端调用实例
        // 文件上传的endPoint为file域名
        EssClient client = Client.getEssFileClient();

        // 构造请求体
        UploadFilesRequest request = new UploadFilesRequest();

        // 调用方用户信息，参考通用结构
        Caller caller = new Caller();
        caller.setOperatorId(operatorId);
        request.setCaller(caller);

        request.setBusinessType("DOCUMENT");

        // 上传文件内容
        UploadFile[] fileInfos = new UploadFile[1];
        request.setFileInfos(fileInfos);
        UploadFile file = new UploadFile();
        fileInfos[0] = file;

        file.setFileName(fileName);
        file.setFileBody(fileBase64);

        UploadFilesResponse response = client.UploadFiles(request);

        String[] fileIds = response.getFileIds();
        return fileIds[0];

    }

    private static String getBase64FileBody(String inputFilePath) throws IOException {
        File file = new File(inputFilePath);
        return getBase64FileBody(file);
    }

    private static String getBase64FileBody(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        return getBase64FileBody(fileInputStream);
    }

    private static String getBase64FileBody(FileInputStream fileInputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len;
        while ((len = fileInputStream.read(bytes)) != -1) {
            baos.write(bytes, 0, len);
        }
        byte[] fileBytes = baos.toByteArray();

        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(fileBytes);
    }


    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            String fileName = "****************";

            // 将文件处理为Base64编码后的文件内容
            String inputFilePath = "src/main/resources/blank.pdf";
            String fileId = UploadFilesApi.UploadFile(Config.OperatorUserId, fileName, getBase64FileBody(inputFilePath));
            System.out.println("fileId: " + fileId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
