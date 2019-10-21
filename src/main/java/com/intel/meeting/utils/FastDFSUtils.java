package com.intel.meeting.utils;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Intel-Meeting
 * @create 2019-05-31 15:37
 */
@PropertySource("classpath:application.properties")
public class FastDFSUtils {

    /**
     * @param FDFSDFS_CLIENT_PAHT fdfs_client.properties
     * @param FDFSDFS_ADDRESS     FastDFS图片服务器地址
     * @param file                上传的文件
     * @return 返回上传之后的URL
     */
    public static String uploadFile(String FDFSDFS_CLIENT_PAHT, String FDFSDFS_ADDRESS, MultipartFile file) {

        File fdfsConfFile = new File(FDFSDFS_CLIENT_PAHT);
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

        String imgUrl = null;
        try {

            //本地写法
//            ClassPathResource cpr = new ClassPathResource(FDFSDFS_CLIENT_PAHT);
//            ClientGlobal.init(cpr.getClassLoader().getResource(FDFSDFS_CLIENT_PAHT).getPath());

            ClientGlobal.init(fdfsConfFile.getPath());

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer,
                    storageServer);
            NameValuePair nvp[] = new NameValuePair[]{
                    new NameValuePair("item_id", "100010"),
                    new NameValuePair("width", "80"),
                    new NameValuePair("height", "90")
            };
            String fileIds[] = storageClient.upload_file(file.getBytes(), ext, nvp);

            imgUrl = "http://" + FDFSDFS_ADDRESS + "/" + fileIds[0] + "/" + fileIds[1];

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imgUrl;
    }

}
