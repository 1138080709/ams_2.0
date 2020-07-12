package com.ams.system.controller;

import com.ams.common.utils.PageResultBean;
import com.ams.system.entity.File;
import com.ams.common.utils.ResultBean;
import com.ams.system.service.FileService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: WuWeiquan
 * @Date: 2020/5/28 18:20
 */
@Controller
@RequestMapping("/file")
public class FileController {

    @Resource
    private FileService fileService;

    @GetMapping("index")
    public String index() {
        return "file/file-list";
    }

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    @ResponseBody
    public ResultBean upload(@RequestParam("multipartFile[]") MultipartFile[] multipartFiles, @RequestParam("id") Integer dirId) throws IOException {
        System.out.println(multipartFiles[0].getContentType());
        fileService.upload(multipartFiles, dirId);
        return ResultBean.success();
    }

//    @ApiOperation("下载文件")
//    @GetMapping("/download/{fileId}")
//    public void download(@PathVariable("fileId") Integer fileId, HttpServletResponse response) throws IOException {
//        fileService.download(fileId, response);
//    }

    @ApiOperation("下载文件")
    @GetMapping("/download/{fileId}")
    @ResponseBody
    public ResultBean download(@PathVariable("fileId") Integer fileId) throws IOException {
        File file = fileService.selectFileById(fileId);
        return ResultBean.success(file);
    }
    
//    @ApiOperation("下载文件")
//    @PostMapping("/download")
//    @ResponseBody
//    public ResultBean download(@RequestParam(value = "fileId[]",required = true) Integer[] fileId, HttpServletResponse response) throws IOException {
//        fileService.downloadWithFileIds(fileId, response);
//        return ResultBean.success();
//    }

//    @ApiOperation("删除文件(/文件夹)")
//    @DeleteMapping("/{fileId}")
//    @ResponseBody
//    public ResultBean del(@PathVariable("fileId") Integer fileId) {
//        fileService.delFile(fileId);
//        return ResultBean.success();
//    }

    @ApiOperation("删除文件(/文件夹)")
    @DeleteMapping
    @ResponseBody
    public ResultBean del(@RequestParam(value = "fileId[]",required = true) Integer[] fileId) {
        fileService.delFileWithFileIds(fileId);
        return ResultBean.success();
    }

    @ApiOperation("编辑文件(重命名)")
    @PutMapping
    @ResponseBody
    public ResultBean edit(File file) {
        fileService.editFile(file);
        return ResultBean.success();
    }

    @ApiOperation("新建文件夹")
    @PostMapping("/folder")
    @ResponseBody
    public ResultBean addFolder(@Validated File file) {
        return ResultBean.success(fileService.addFolder(file));
    }

//    @ApiOperation("删除文件夹")
//    @DeleteMapping("/folder/{fileId}")
//    @ResponseBody
//    public ResultBean delFolder(@PathVariable("fileId") Integer fileId) {
//        fileService.delFolder(fileId);
//        return ResultBean.success();
//    }

//    @ApiOperation("编辑文件夹信息")
//    @PutMapping("/folder")
//    @ResponseBody
//    public ResultBean editFolder(File file) {
//        fileService.editFolder(file);
//        return ResultBean.success();
//    }

    @ApiOperation("获取文件列表")
    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<File> getlist(@RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "limit", defaultValue = "10") int limit,
                                  File fileQuery) {
        List<File> files = fileService.selectAllWithQuery(page,limit,fileQuery);
        PageInfo<File> filePageInfo = new PageInfo<>(files);
        return new PageResultBean<>(filePageInfo.getTotal(),filePageInfo.getList());
    }
    
    
    @ApiOperation("复制与剪切")
    @PutMapping("/paste")
    @ResponseBody
    public ResultBean paste(@RequestParam(value = "fileId[]",required = true) Integer[] fileId,
                            @RequestParam(value = "type",required = true)Integer type,Integer dirId) {
        fileService.paste(fileId,type,dirId);
        return ResultBean.success();
    }
    
}
