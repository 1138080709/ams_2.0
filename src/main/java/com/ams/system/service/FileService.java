package com.ams.system.service;

import com.ams.common.exception.FileDeleteException;
import com.ams.common.exception.FileDownloadException;
import com.ams.common.exception.FileUploadException;
import com.ams.common.exception.UnSupportFileFormatException;
import com.ams.common.utils.FastDFSUtils;
import com.ams.common.utils.FileUtils;
import com.ams.system.entity.File;
import com.ams.system.dao.FileMapper;
import com.ams.system.entity.User;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.csource.common.NameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


/**
 * @Author: WuWeiquan
 * @Date: 2020/6/13 0:17
 */
@Service
public class FileService {

    @Resource
    private FileMapper fileDao;

    @Value("${ams.file.format}")
    private String fileFormat;

    @Transactional
    public void upload(MultipartFile[] multipartFiles, Integer dirId) throws IOException {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        for (MultipartFile multipartFile : multipartFiles) {
            NameValuePair[] meta = new NameValuePair[2];
            String fileName = multipartFile.getOriginalFilename();
            String fileExtName = FileUtils.getFileExtName(fileName);
            if (!isAllow(fileExtName)) {
                throw new UnSupportFileFormatException();
            }
            meta[0] = new NameValuePair("fileName", fileName);
            meta[1] = new NameValuePair("author", user.getUsername());
            String[] arr = FastDFSUtils.uploadFile(multipartFile.getBytes(), fileExtName, meta);
            if (arr == null || arr.length != 2) {
                throw new FileUploadException();
            }
            File file = new File(arr[0], arr[1], multipartFile.getOriginalFilename(), fileExtName, dirId, user.getUsername());
            fileDao.insert(file);
        }
    }
    
    // 判断上传文件的格式是否支持
    private boolean isAllow(String fileExtName) {
        String[] formats = fileFormat.split("\\|");
        for (String format : formats) {
            if(fileExtName.equals(format)) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void download(Integer fileId, HttpServletResponse response) throws IOException {
        File file = fileDao.selectByPrimaryKey(fileId);
        if (file == null || file.getFileName() == null || file.getGroupName() == null || file.getRemoteFileName() == null) {
            throw new FileDownloadException();
        }
        response.reset();
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getFileName().getBytes("GBK"), "ISO-8859-1"));
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        OutputStream outputStream = response.getOutputStream();
        if (!FastDFSUtils.downloadFile(file.getFileName(), file.getGroupName(), file.getRemoteFileName(), outputStream)) {
            throw new FileDownloadException();
        }
    }

    @Transactional
    public void delFile(Integer fileId) {
        File file = fileDao.selectByPrimaryKey(fileId);
        if (file == null || file.getFileName() == null || file.getGroupName() == null || file.getRemoteFileName() == null) {
            throw new FileDeleteException();
        }
        if (!"dir".equals(file.getType())) {
            if (!FastDFSUtils.delFile(file.getFileName(), file.getGroupName(), file.getRemoteFileName())) {
                throw new FileDeleteException();
            }
        }
        fileDao.deleteByPrimaryKey(fileId);
    }

    public Integer addFolder(File file) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        file.setCreator(user.getUsername());
        file.setType("dir");
        return fileDao.insert(file);
    }

    public void delFolder(Integer fileId) {
        fileDao.deleteByPrimaryKey(fileId);
    }

    public void editFolder(File file) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        file.setUpdator(user.getUsername());
        fileDao.updateByPrimaryKey(file);
    }

    @Transactional
    public void editFile(File file) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        file.setUpdator(user.getUsername());
        if (!"dir".equals(file.getType())) {
            if (file.getFileName() != null && !"".equals(file.getFileName())) {
                File oldFile = fileDao.selectByPrimaryKey(file.getFileId());
                NameValuePair meta = new NameValuePair("fileName", file.getFileName());
                FastDFSUtils.editFileMeta(oldFile.getGroupName(), oldFile.getRemoteFileName(), meta);
            }
        }
        fileDao.updateByPrimaryKey(file);
    }


    public List<File> selectAllWithQuery(int page, int limit, File fileQuery) {
        if (fileQuery.getDirId() == null) {
            fileQuery.setDirId(0);
        }
        PageHelper.startPage(page, limit);
        return fileDao.selectAllWithQuery(fileQuery);
    }

    @Transactional
    public void delFileWithFileIds(Integer[] fileIds) {
        for (Integer fileId : fileIds) {
            File file = fileDao.selectByPrimaryKey(fileId);
            if (file == null) {
                throw new FileDeleteException("删除发生错误：没有找到文件id为" + fileId + "的文件。");
            }
            if (!"dir".equals(file.getType())) {
                if (file.getFileName() == null || file.getGroupName() == null || file.getRemoteFileName() == null
                        || !FastDFSUtils.delFile(file.getFileName(), file.getGroupName(), file.getRemoteFileName())) {
                    throw new FileDeleteException();
                }
            }
            fileDao.deleteByPrimaryKey(fileId);
        }
    }

    @Transactional
    public void downloadWithFileIds(Integer[] fileIds, HttpServletResponse response) throws IOException {
        for (Integer fileId : fileIds) {
            File file = fileDao.selectByPrimaryKey(fileId);
            if (file == null) {
                throw new FileDownloadException("下载文件发生错误：没有找到文件id为" + fileId + "的文件。");
            }
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getFileName().getBytes("GBK"), "ISO-8859-1"));
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            OutputStream outputStream = response.getOutputStream();
            if (file.getFileName() == null || file.getGroupName() == null || file.getRemoteFileName() == null
                    || !FastDFSUtils.downloadFile(file.getFileName(), file.getGroupName(), file.getRemoteFileName(), outputStream)) {
                throw new FileDownloadException();
            }
        }
    }

    public void paste(Integer[] fileIds, Integer type, Integer dirId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 剪切
        if (type == 0) {
            for (Integer fileId : fileIds) {
                File file = new File();
                file.setFileId(fileId);
                file.setDirId(dirId);
                file.setUpdator(user.getUsername());
                fileDao.updateByPrimaryKey(file);
            }
        } else { //复制
            for (Integer fileId : fileIds) {
                File file = fileDao.selectByPrimaryKey(fileId);
                file.setDirId(dirId);
                fileDao.insert(file);
            }
        }
    }

    public File selectFileById(Integer fileId) {
        return fileDao.selectByPrimaryKey(fileId);
    }
}
