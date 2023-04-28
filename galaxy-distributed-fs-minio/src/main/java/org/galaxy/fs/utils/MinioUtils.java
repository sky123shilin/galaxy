package org.galaxy.fs.utils;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.galaxy.fs.config.MinioProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MinioUtils {

    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioProperties minioProperties;

    /**
     * 判断一个bucket是否存在
     * @param bucketName  bucket名称
     * @return 布尔值
     */
    public boolean bucketExists(String bucketName) {
        boolean flag = false;
        try {
            flag = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException |
                 IOException e) {
            log.error("发生异常：{}", e.getMessage());
        }
        return flag;
    }

    /**
     * 创建一个bucket
     * @param bucketName bucket名称
     * @return 布尔值
     */
    public boolean createBucket(String bucketName) {
        boolean flag = false;
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            flag = true;
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 IOException | NoSuchAlgorithmException | ServerException | XmlParserException |
                 InvalidResponseException e) {
            log.error("发生异常：{}", e.getMessage());
        }
        return flag;
    }

    /**
     * 删除一个bucket
     * @param bucketName bucket名称
     * @return 布尔值
     */
    public boolean removeBucket(String bucketName) {
        boolean flag = false;
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
            flag = true;
        } catch (ErrorResponseException | InsufficientDataException | XmlParserException | ServerException |
                 NoSuchAlgorithmException | InvalidResponseException | InvalidKeyException | InternalException |
                 IOException e) {
            log.error("发生异常：{}", e.getMessage());
        }
        return flag;
    }

    /**
     * 基于一个对象的相对路径获取临时链接（即浏览器中可以直接访问下载的连接）
     * @param objectPath 对象的相对路径（即相对于bucket）
     * @param expires 临时链接的过期时间
     * @return url
     */
    public String previewObject(String objectPath, Integer expires) {
        if (Objects.isNull(objectPath)) {
            return null;
        }
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(objectPath)
                            .expiry(expires, TimeUnit.SECONDS)
                            .method(Method.GET)
                            .build()
            );
        } catch (ErrorResponseException | InsufficientDataException | InvalidKeyException | InvalidResponseException |
                 IOException | NoSuchAlgorithmException | InternalException | XmlParserException | ServerException e) {
            log.error("发生异常：{}", e.getMessage());
        }
        return null;
    }

    /**
     * 基于一个对象的相对路径获取临时链接（即浏览器中可以直接访问下载的连接）
     * @param objectPath 对象的相对路径（即相对于bucket）
     * @return url
     */
    public String previewObject(String objectPath) {
        return previewObject(objectPath,604800);
    }

    /**
     * 根据一个对象路径获取对象流
     * @param objectPath 对象路径
     * @return 对象数据流
     */
    public InputStream getObject(String objectPath) {
        if (Objects.isNull(objectPath)) {
            return null;
        }
        try {
            GetObjectResponse response = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(objectPath)
                            .build()
            );
            if (Objects.isNull(response)) {
                return null;
            }
            return response;
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException |
                 IOException e) {
            log.error("发生异常：{}", e.getMessage());
        }
        return null;
    }

    /**
     * 根据一个对象路径，删除一个对象
     * @param objectPath 对象路径
     * @return 是否删除成功
     */
    public boolean removeObject(String objectPath) {
        if (Objects.isNull(objectPath)) {
            return true;
        }
        boolean flag = false;
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(objectPath)
                    .build()
            );
            flag = true;
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException |
                 IOException e) {
            log.error("发生异常：{}", e.getMessage());
        }
        return flag;
    }

    /**
     * 上传一个对象到Minio
     * @param objectPath 对象路径
     * @param resource 对象的数据字节数组
     * @return 返回上传后的完整路径
     */
    public String uploadObject(String objectPath, org.springframework.core.io.Resource resource) {
        try {
            return uploadObject(objectPath, resource.getInputStream());
        } catch (IOException e) {
            log.error("发生异常：{}", e.getMessage());
        }
        return null;
    }

    /**
     * 上传一个对象到Minio
     * @param objectPath 对象路径
     * @param bytes 对象的数据字节数组
     * @return 返回上传后的完整路径
     */
    public String uploadObject(String objectPath, byte[] bytes) {
        InputStream is = new ByteArrayInputStream(bytes);
        return uploadObject(objectPath, is);
    }

    /**
     * 上传一个对象到Minio
     * @param objectPath 对象路径
     * @param is 文件输入流
     * @return 返回上传后的完整路径
     */
    public String uploadObject(String objectPath, InputStream is) {
        if (Objects.isNull(objectPath)) {
            return null;
        }
        try {
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(minioProperties.getBucketName())
                            .object(objectPath)
                            .stream(is, is.available(),-1)
                            .build()
            );
            return objectPath;
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException |
                 IOException e) {
            log.error("发生异常：{}", e.getMessage());
        } finally {
            if (Objects.nonNull(is)) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 基于一个对象路径，将这个对象直接下载到浏览器中
     * @param objectPath 对象路径
     * @param servletResponse 请求响应
     */
    public void downLoadObject(String objectPath, HttpServletResponse servletResponse) {
        if (Objects.isNull(objectPath)) {
            return;
        }
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(minioProperties.getBucketName()).object(objectPath).build();
        try (GetObjectResponse response = minioClient.getObject(objectArgs)){
            byte[] buf = new byte[1024];
            int len;
            try (ByteArrayOutputStream os = new ByteArrayOutputStream()){
                while ((len = response.read(buf)) != -1) {
                    os.write(buf,0,len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.toString());
                servletResponse.addHeader("Content-Disposition", "attachment;fileName=" + objectPath);
                try (ServletOutputStream stream = servletResponse.getOutputStream()){
                    stream.write(bytes);
                    stream.flush();
                }
            }
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException |
                 IOException e) {
            log.error("发生异常：{}", e.getMessage());
        }
    }

}
