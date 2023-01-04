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

/**
 * 此接口（UploadFiles）用于文件上传。
 * <p>
 * 官网文档：https://cloud.tencent.com/document/api/1323/73066
 * <p>
 * 适用场景：用于生成pdf资源编号（FileIds）来配合“用PDF创建流程”接口使用，使用场景可详见“用PDF创建流程”接口说明
 * 调用是请注意此处的 Endpoint 和其他接口不同
 */
public class UploadFilesApi {
    /**
     * 通过filePath上传文件
     *
     * @param operatorId 经办人id
     * @param filePath   文件路径
     * @return 文件fileId
     */
    public static String UploadFile(String operatorId, String filePath)
            throws TencentCloudSDKException, IOException {

        String fileBase64 = getBase64FileBody(filePath);
        File file = new File(filePath);
        String fileName = file.getName();
        return UploadFile(operatorId, fileName, fileBase64);
    }

    /**
     * 通过fileBytes上传文件
     *
     * @param operatorId 经办人id
     * @param fileName   文件名
     * @param fileBytes  文件字节
     * @return 文件fileId
     */
    public static String UploadFile(String operatorId, String fileName, byte[] fileBytes)
            throws TencentCloudSDKException {

        Base64.Encoder encoder = Base64.getEncoder();
        String fileBase64 = encoder.encodeToString(fileBytes);
        return UploadFile(operatorId, fileName, fileBase64);
    }

    /**
     * 通过fileBase64上传文件
     *
     * @param operatorId 经办人id
     * @param fileName   fileName
     * @param fileBase64 文件内容的Base64编码
     * @return 文件fileId
     */
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

        // 文件对应业务类型，用于区分文件存储路径：
        // 1. TEMPLATE - 模板； 文件类型：.pdf/.html
        // 2. DOCUMENT - 签署过程及签署后的合同文档 文件类型：.pdf/.html
        // 3. SEAL - 印章； 文件类型：.jpg/.jpeg/.png
        request.setBusinessType("DOCUMENT");

        // 上传文件内容
        UploadFile[] fileInfos = new UploadFile[1];
        request.setFileInfos(fileInfos);
        UploadFile file = new UploadFile();
        fileInfos[0] = file;

        // 文件名，最大长度不超过200字符
        file.setFileName(fileName);

        // Base64编码后的文件内容
        file.setFileBody(fileBase64);

        UploadFilesResponse response = client.UploadFiles(request);

        String[] fileIds = response.getFileIds();
        return fileIds[0];

    }

    /**
     * 获取文件base64
     *
     * @param inputFilePath 文件路径
     * @return 文件base64
     */
    private static String getBase64FileBody(String inputFilePath) throws IOException {
        File file = new File(inputFilePath);
        return getBase64FileBody(file);
    }

    /**
     * 获取文件base64
     *
     * @param file 文件
     * @return 文件base64
     */
    private static String getBase64FileBody(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        return getBase64FileBody(fileInputStream);
    }

    /**
     * 获取文件base64
     *
     * @param fileInputStream 文件流
     * @return 文件base64
     */
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
            // 文件名，最大长度不超过200字符
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
