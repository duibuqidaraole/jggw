package com.mt.government.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.mt.government.common.exception.GlobalException;
import com.mt.government.common.util.FileUtils;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件controller
 *
 * @author fuzhigang
 */
@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    public static String IMAGEUPLOADPATH = "/opt/jiguan/images/";

    /**
     * 文件存放路径
     */
    @Value("${FILEPATH}")
    private String FILEPATH;

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 执行结果
     */
    @RequestMapping("/fileUpload")
    public Result fileUpload(@RequestParam("file") MultipartFile file) {
        // 如果文件为空返回错误信息
        if (file.isEmpty()) {
            return ResultUtil.error("上传失败，请选择文件");
        }
        try {
            // 获取文件名
            String originalFilename = file.getOriginalFilename();
            // 获取文件类型
            String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 设置新的文件名
            String newFilename = IdUtil.simpleUUID() + ext;
            // 使用当前年月为文件夹，防止同文件下文件名过多
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            StringBuilder dir = new StringBuilder();
            dir.append(c.get(Calendar.YEAR)).append("-").append(c.get(Calendar.MONTH) + 1);

            // 创建文件
            File dest = FileUtil.touch(FILEPATH + dir + "/" + newFilename);
            // 写入文件内容
            file.transferTo(dest);
            // 返回文件名和文件地址
            StringBuilder url = dir.append("/").append(newFilename);
            Map<String, String> map = new HashMap<>(4);
            map.put("fileName" , originalFilename);
            map.put("url" , url.toString());
            return ResultUtil.success(map);
        } catch (Exception e) {
            LOGGER.error("上传文件失败" , e);
            return ResultUtil.error("上传文件失败");
        }
    }

    /**
     * 图片上传
     *
     * @param file
     * @return 图片地址
     */
    @RequestMapping("/imageUpload")
    public Result imageUpload(MultipartFile file) {
        if (file.isEmpty()) {
            return ResultUtil.error("file is empty");
        }

        try {
            // 获取文件名
            String originalFilename = file.getOriginalFilename();
            // 获取文件类型
            String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 设置新的文件名
            String newFilename = IdUtil.simpleUUID() + ext;

            // 创建新文件
            File image = FileUtil.touch(IMAGEUPLOADPATH + newFilename);
            // 写入文件信息
            file.transferTo(image);
            // 返回图片地址
            return ResultUtil.success("/images/" + newFilename);
        } catch (FileNotFoundException e) {
            LOGGER.error("图片上传失败" , e);
            return ResultUtil.error("图片上传失败");
        } catch (IOException e) {
            LOGGER.error("创建文件失败" , e);
            return ResultUtil.error("图片上传失败");
        }
    }

    /**
     * 下载文件
     *
     * @param fileAttachment 文件全路径
     * @param response
     * @return 执行结果
     */
    @GetMapping("/fileDownload")
    public void fileDownload(String fileAttachment, String fileName, HttpServletResponse response, HttpServletRequest request) {

        // 获取文件
        File file = new File(FILEPATH + fileAttachment);
        if (FileUtil.isEmpty(file)) {
            throw new GlobalException("文件不存在" , -1);
        }
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition" ,
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, fileName));

            FileUtils.writeBytes(file, response.getOutputStream());
        } catch (Exception e) {
            LOGGER.error("下载文件失败" , e);
        }
    }
}