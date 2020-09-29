package com.mt.government.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.mt.government.common.exception.GlobalException;
import com.mt.government.common.util.FileUtils;
import com.mt.government.model.Due2;
import com.mt.government.model.Dues;
import com.mt.government.model.User;
import com.mt.government.service.DuesService;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.converter.ExcelToHtmlUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Collections;
import java.util.Date;

/**
 * 党费信息管理controller
 *
 * @author g
 * @date 2019-11-12 14:11
 */
@RestController
@RequestMapping("/party")
@CrossOrigin
public class DueController extends BaseController {

    @Autowired
    private DuesService duesService;

    private static String EXCELDIR = "demo/";
    private static String DANGFEI = "党费样表.xlsx";
    private static String SHUOMING = "系统操作说明.docx";

    /**
     * 新增党费信息
     *
     * @param due2 党费信息
     * @return 更新结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody Due2 due2, HttpServletRequest request) {
        if (StringUtils.isEmpty(due2.getUserId())) {
            due2.setUserId(getCurrentUser(request).getUserId());
        }
        return ResultUtil.success(duesService.save(due2));
    }

    @PostMapping("/update")
    public Result update(@RequestBody Due2 due2, HttpServletRequest request) {
        if (due2.getUserId() != null && !due2.getUserId().equals(getCurrentUser(request).getUserId())) {
            return ResultUtil.error("您没有权限修改其他账号的信息");
        }
        return ResultUtil.success(duesService.update(due2));
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id) {
        return ResultUtil.success(duesService.delete(id));
    }

    /**
     * 获取用户党费信息
     */
    @GetMapping("/getDueInfo")
    public Result getDueInfo(String userId) {
        return duesService.getDuesInfoByUser(userId);
    }


    /**
     * 导入党费信息
     * @param file excel文件
     * @param type 操作类型 1：新增 2：覆盖
     */
    @PostMapping("/importData")
    public Result importData(MultipartFile file, Integer type, HttpServletRequest request) {
        if (type == null || (type != 1 && type != 2)) {
            return ResultUtil.error("不支持的操作类型");
        }
        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        // 校验文件
        if (file.isEmpty() || (!ext.equals("xlsx") && !ext.equals("xls"))) {
            return ResultUtil.error("请上传正确的文件");
        }
        User user = getCurrentUser(request);
        try {
           int ref = duesService.resolveExcelToDues(file, type, user.getUserId());
           return ResultUtil.success(ref);
        } catch (IOException e) {
            throw new GlobalException("导入信息失败", -1);
        }
    }

    /**
     * 下载文档
     *
     * @param type 表格类型 0:公务员 1:事业单位 2:企业单位
     */
    @GetMapping("/downloadDemo")
    public void downloadDemo(@NotNull(message = "类型不能为空") Integer type, HttpServletResponse response, HttpServletRequest request) {
        String fileName;
        String filePath;
        // 根据类型获取文件路径
        switch (type) {
            case 1:
                fileName = DANGFEI;
                filePath = EXCELDIR + DANGFEI;
                break;
            case 2:
                fileName = SHUOMING;
                filePath = EXCELDIR + SHUOMING;
                break;
            default:
                throw new GlobalException("参数错误，请使用正常的操作" , -1);
        }
        try {
            // 设置响应头
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition" ,
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request,
                            fileName));
            // 读取文件并输出
            InputStream in = new ClassPathResource(filePath).getInputStream();
            OutputStream out = response.getOutputStream();
            IoUtil.copy(in, out);
            // may throw Exception
            out.flush();
            IoUtil.close(in);
            IoUtil.close(out);
        } catch (Exception e) {
            throw new GlobalException("下载党费样表失败" , -1);
        }
    }
}
