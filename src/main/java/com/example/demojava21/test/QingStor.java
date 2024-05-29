package com.example.demojava21.test;

import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.request.RequestHandler;
import com.qingstor.sdk.request.ResponseCallBack;
import com.qingstor.sdk.service.Bucket;
import com.qingstor.sdk.utils.QSSignatureUtil;

import java.io.File;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * QingStor
 *
 * <p>创建人：hrniu 创建日期：2024/5/21
 */
public class QingStor {
    public static void main(String[] args){
        javaSdkSendDemo();
    }

    public static void javaSdkSendDemo() {
        try {
            // 创建EvnContext并设置自定义endpoint
            EnvContext env = new EnvContext("KWDBUDFFJREOYXCUIPFL", "RiwuJh3qbS9ry8dcOzfNckojCULMVhP8fjS8WZlM");
            env.setEndpoint("http://yxqc.chinalife-p.com.cn:80");

            // bucket所在的zone
            String zoneName = "sh1";

            // bucket名称
            String bucketName = "sh-obs-test";
            Bucket bucket = new Bucket(env, zoneName, bucketName);

            // 最终上传到对象存储的文件显示的文件名称
            String objectKey = "aa.zip";
            Bucket.PutObjectInput input = new Bucket.PutObjectInput();

            // 要上传的本地文件的路径
            File f = new File("C:\\Users\\87028\\Desktop\\aa.zip");
            input.setBodyInputFile(f);
            input.setContentLength(f.length());
            RequestHandler reqHandler = bucket.putObjectAsyncRequest(objectKey, input,
                    new ResponseCallBack<Bucket.PutObjectOutput>() {
                        public void onAPIResponse(Bucket.PutObjectOutput output) {
                            if (output.getStatueCode() != 201) {
                                System.out.println("Message = " + output.getMessage());
                                System.out.println("RequestId = " + output.getRequestId());
                                System.out.println("Code = " + output.getCode());
                                System.out.println("StatueCode = " + output.getStatueCode());
                                System.out.println("Url = " + output.getUrl());
                            }
                            System.exit(0);
                        }
                    });

            Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
            String gmtTime = QSSignatureUtil.formatGmtDate(instance.getTime());

            // 验证需要这个Date header
            reqHandler.getBuilder().setHeader(QSConstant.HEADER_PARAM_KEY_DATE, gmtTime);
            String strToSignature = reqHandler.getStringToSignature();
            String serverAuthorization = QSSignatureUtil.generateSignature(env.getSecretAccessKey(),
                    strToSignature);
            reqHandler.setSignature(env.getAccessKeyId(), serverAuthorization);
            // 异步发送
            reqHandler.sendAsync();

        } catch (QSException e) {
            e.printStackTrace();
        }
    }


}
