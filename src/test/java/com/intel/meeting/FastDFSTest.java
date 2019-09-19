package com.intel.meeting;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Intel-Meeting
 * @create 2019-09-07 16:32
 */
public class FastDFSTest {

    public static void main(String[] args) {
        String conf_filename = "D:\\Code-workspace\\IdeaProjects\\spring-boot\\intel-meeting\\src\\main\\resources\\fdfs_client.properties";
        //本地文件，要上传的文件
        String local_filename = "D:\\zz-file\\img\\fastdfs.jpeg";

        try {
            ClientGlobal.init(conf_filename);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);

            String fileIds[] = storageClient.upload_file(local_filename, null,null);

            System.out.println(fileIds.length);
            System.out.println("组名：" + fileIds[0]);
            System.out.println("路径: " + fileIds[1]);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
