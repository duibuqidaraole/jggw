package com.mt.government.common.config;

import com.mt.government.common.exception.GlobalException;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

/**
 * 全局异常处理器
 *
 * @author fuzhigang
 * @version 1.0
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandle {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandle.class);
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxSize;

    @ExceptionHandler(Exception.class)
    public Result exceptionHandle(Exception e) {
        if (e instanceof GlobalException) {
            return ResultUtil.error(e.getMessage());
        } else {
            LOGGER.error("系统异常" , e);
            return ResultUtil.error("操作失败，请检查数据是否正确");
        }
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Result multipartExceptionHandler(MultipartException e) {
        LOGGER.error("文件上传失败" , e);
        if (e.getCause().getCause() instanceof FileUploadBase.FileSizeLimitExceededException) {
            return ResultUtil.error("文件上传失败, 大小不能超过" + maxSize);
        } else if (e.getCause().getCause() instanceof FileUploadBase.SizeLimitExceededException) {
            return ResultUtil.error("文件上传失败, 总大小不能超过100MB");
        } else {
            return ResultUtil.error("文件上传失败");
        }
    }
}
