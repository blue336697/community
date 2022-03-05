package com.lhjitem.community.provider;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.util.resources.CalendarData;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author lhj
 * @create 2022/3/4 15:11
 * 如果想要设置url的过期时间，要先将bucket设置成私有，然后使用
 * // 设置URL过期时间为1小时。
 *         Date expiration = new Date(System.currentTimeMillis() + 3600 * 100000);
 *
 *         String url = ossClient.generatePresignedUrl(bucketName, objectName, expiration).toString();
 *
 */
@Service
public class AliCloudProvider {
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    @Value("${aliyun.ufile.public-key}")
    private String accessKeyId;

    @Value("${aliyun.ufile.private-key}")
    private String accessKeySecret;

    // 填写Bucket名称，例如examplebucket。
    @Value("${aliyun.ufile.bucketName}")
    private String bucketName;

    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。关于其他Region对应的Endpoint信息，请参见访问域名和数据中心。
    @Value("${aliyun.ufile.endpoint}")
    private String endpoint;

    public String upload(InputStream fileStream, String fileType, String originalFilename){


        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
//        String objectName = "exampledir/exampleobject.txt";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //使用更复杂更具特征的文件名
        String folder = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        String fileName = UUID.randomUUID().toString();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        // oss中的文件夹名
        String objectName = "lhj" + "/" + folder + "/" + fileName + fileExtension;

        //创建上传文件的元信息，可以通过文件元信息设置HTTP header(设置了才能通过返回的链接直接访问)。
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(fileType);

        // 设置图片大小
//        String style = "image/resize,m_fixed,w_100,h_100";
//        GetObjectRequest request = new GetObjectRequest(bucketName, objectName);
//        request.setProcess(style);
        try {
            // 创建存储空间。
            ossClient.createBucket(bucketName);
//            String content = "Hello OSS";
            ossClient.putObject(bucketName, objectName, fileStream, objectMetadata);

            ossClient.shutdown();
            //由于我们使用的公共读，别人并没有写的
            String url = "http://"+bucketName+"."+endpoint+"/"+objectName;
            return url;

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }

}
