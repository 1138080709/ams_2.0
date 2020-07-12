package com.ams.common.utils;

import cn.hutool.core.io.resource.ClassPathResource;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Client;

import java.io.*;

/**
 * 构建FastDFS client,提供对FastDFS server的基本操作
 *
 * @Author: WuWeiquan
 * @Date: 2020/6/11 16:43
 */
public class FastDFSUtils {

    private static final Logger log = LoggerFactory.getLogger(FastDFSUtils.class);

    private static TrackerClient trackerClient;

    static {
        ClassPathResource pr = new ClassPathResource("fdfs-client.conf");
        try {
            ClientGlobal.init(pr.getClassLoader().getResource("fdfs-client.conf").getPath());
            trackerClient = new TrackerClient();
        } catch (IOException | MyException e) {
            log.debug(e.getMessage());
        }
    }

    /**
     * 向FastDFS上传文件
     *
     * @param data        文件数据
     * @param fileExtName 文件扩展名
     * @param meta        键值对数据 (meta[0]数据库中ID meta[1]文件名 meta[2]上传者)
     * @return String[]   String[0]组名(groupName) String[1]文件名(remoteFileName)
     */
    public static String[] uploadFile(byte[] data, String fileExtName, NameValuePair[] meta) {
        TrackerServer trackerServer = null;
        String[] arr = null;
        try {
            trackerServer = trackerClient.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer, null);
            arr = storageClient.upload_file(data, fileExtName, meta);
        } catch (IOException | MyException e) {
            log.debug(e.getMessage());
        } finally {
            closeTrackerServer(trackerServer);
        }
        return arr;
    }

    /**
     * 向FastDFS下载文件
     *
     * @param localFileName  本地文件名
     * @param groupName      组名 (文件在FastDFS中的组名)
     * @param remoteFileName 文件名 (文件在FastDFS中的文件名)
     * @param outputStream   输出流
     */
    public static boolean downloadFile(String localFileName, String groupName, String remoteFileName, OutputStream outputStream) throws IOException {
        TrackerServer trackerServer = null;
        boolean result = true;
        try {
            trackerServer = trackerClient.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer, null);
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            long startTime = System.currentTimeMillis();
            byte[] data = storageClient.download_file(groupName, remoteFileName);
            long overTime = System.currentTimeMillis();
            log.info("文件下载时间："+ (overTime-startTime)/1000 + "s" );
            if (data == null || data.length == 0) {
                result = false;
                log.info(localFileName + "文件下载失败");
            }
            bos.write(data);
            log.info(localFileName + "文件下载成功");
        } catch (IOException | MyException e) {
            result = false;
            log.debug(e.getMessage());
        } finally {
            closeTrackerServer(trackerServer);
            outputStream.close();
        }
        return result;
    }

    /**
     * 删除FastDFS中文件
     *
     * @param localFileName  本地文件名
     * @param groupName      组名
     * @param remoteFileName 文件名
     * @return
     */
    public static boolean delFile(String localFileName, String groupName, String remoteFileName) {
        TrackerServer trackerServer = null;
        boolean result = false;
        try {
            trackerServer = trackerClient.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer, null);
            int i = storageClient.delete_file(groupName, remoteFileName);
            if (i == 0) {
                result = true;
                log.info(localFileName + "文件删除成功");
            } else {
                log.info(localFileName + "文件删除失败");
            }
        } catch (IOException | MyException e) {
            log.debug(e.getMessage());
        } finally {
            closeTrackerServer(trackerServer);
        }
        return result;
    }

    /**
     * 更新FastDFS中文件的meta信息
     *
     * @param groupName
     * @param remoteFileName
     * @param newMeta
     */
    public static void editFileMeta(String groupName, String remoteFileName, NameValuePair newMeta) {
        TrackerServer trackerServer = null;
        try {
            trackerServer = trackerClient.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer, null);
            NameValuePair[] oldMeta = storageClient.get_metadata(groupName, remoteFileName);
            for (NameValuePair meta : oldMeta) {
                if (meta.getName().equals(newMeta.getName())) {
                    meta.setValue(newMeta.getValue());
                }
            }
            storageClient.set_metadata(groupName, remoteFileName, oldMeta, ProtoCommon.STORAGE_SET_METADATA_FLAG_OVERWRITE);
        } catch (IOException | MyException e) {
            log.debug(e.getMessage());
        } finally {
            closeTrackerServer(trackerServer);
        }
    }

    /**
     * 关闭TrackerServer连接
     */
    private static void closeTrackerServer(TrackerServer trackerServer) {
        try {
            if (trackerServer != null) {
                log.info("关闭trackerServer连接");
                trackerServer.close();
            }
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }
}
