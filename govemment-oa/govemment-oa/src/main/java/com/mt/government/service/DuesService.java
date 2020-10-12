package com.mt.government.service;

import com.mt.government.model.Due2;
import com.mt.government.model.Dues;
import com.mt.government.utils.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 党费信息业务接口
 *
 * @author g
 * @date 2019-11-12 11:34
 * @discription
 */
public interface DuesService {

    /**
     * 新增党费信息
     *
     * @param due2 信息
     * @return 保存结果
     */
    int save(Due2 due2);

    /**
     * 修改党费信息
     * @param due2
     * @return
     */
    int update(Due2 due2);

    /**
     * 删除单条党费信息
     * @param id id
     * @return
     */
    int delete(Long id);
    /**
     * 获取用户党费信息表
     *
     * @param userId 用户id
     * @return 信息列表
     */
//    Result getDuesInfoByUser(String userId);

    /**
     * 将excel文件中的数据导入党费
     * @param file 上传的文件
     * @param userId 当前用户
     * @param type  操作类型
     * @return 插入行数
     */
    int resolveExcelToDues(MultipartFile file, Integer type, String userId) throws IOException;
}
