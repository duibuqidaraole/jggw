package com.mt.government.controller;

import cn.hutool.core.io.IoUtil;
import com.github.pagehelper.PageHelper;
import com.mt.government.common.exception.GlobalException;
import com.mt.government.common.util.FileUtils;
import com.mt.government.mapper.Due2Mapper;
import com.mt.government.mapper.OrganizationMapper;
import com.mt.government.mapper.UserMapper;
import com.mt.government.model.*;
import com.mt.government.model.vo.PagedResult;
import com.mt.government.service.DuesService;
import com.mt.government.utils.MyExcelUtil;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.*;

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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Due2Mapper due2Mapper;

    @Autowired
    private OrganizationMapper organizationMapper;

    private static String EXCELDIR = "demo/";
    private static String DANGFEI = "党费样表.xlsx";
    private static String SHUOMING = "系统操作说明.docx";


    /**
     * 导出每月党费信息
     *
     * @param
     * @return
     */
    @GetMapping("/export")
    public void add(String userId, HttpServletResponse response) throws Exception {
        if (StringUtils.isEmpty(userId)) {
            throw new GlobalException("userId不能为空", -1);
        }
        List<Map> mapList = new ArrayList<>();
        String organId = null;

        User user = new User();
        user.setUserId(userId);
        // 1、根据userId，在user表中查询出此用户的role类型  0-工委  1-一级单位 2-二级单位
        User tempUser = userMapper.selectOne(user);
        Integer userRole = tempUser.getUserRole();
        if (userRole.equals(0)) { // 查询出所有的单位的每月党费
            mapList = userMapper.selectAllDue();
        } else if (userRole.equals(1)) { // 查询出一级单位下的所有每月党费
            // 查询出所有的组织id
            organId = userMapper.selectOrganIdByUserId(userId);
            // 将组织id去组织表中查询组织名称  匹配所有的parent_id
            Example example = new Example(Organization.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("parentId", organId);
            // 查询出所有的组织id
            List<Organization> list = organizationMapper.selectByExample(example);
            // 遍历所以的组织id，统计出所有的每月党费
            for (Organization organization : list) {
                Integer orgId = organization.getOrgId();
                Map map = new HashMap();
                map = organizationMapper.selectAllDue02(orgId);
                mapList.add(map);
            }
        } else { // 最底级单位
            Map map = new HashMap();
            map = organizationMapper.selectAllDue03(organId);
            mapList.add(map);
        }

        String headArr[] = {"money", "orgName"};
        String headArrAlias[] = {"每月党费", "单位名称"};
        // 将mapList导出到excel表格中
        MyExcelUtil.getExcel(response, mapList, "每月党费信息.xls", headArr, headArrAlias);

    }


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
    public Result getDueInfo(String userId, Integer page, Integer pageSize, String name) {
        // 开启分页
        PageHelper.startPage(page, pageSize);
        Example example = new Example(Due2.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(userId)) {
            criteria.andEqualTo("userId", userId);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        example.orderBy("createTime").asc();
        List<Due2> due2s = due2Mapper.selectByExample(example);

        return ResultUtil.success(PagedResult.commonResult(due2s));
    }


    /**
     * 导入党费信息
     *
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
                throw new GlobalException("参数错误，请使用正常的操作", -1);
        }
        try {
            // 设置响应头
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition",
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
            throw new GlobalException("下载党费样表失败", -1);
        }
    }

    /**
     * 添加扣分信息
     *
     * @param due2
     * @return
     */
    @PostMapping("addReason")
    public Result addReason(@RequestBody Due2 due2) {
        if (StringUtils.isEmpty(due2.getPoint())) {
            return ResultUtil.error("分值不能为空");
        }
        if (StringUtils.isEmpty(due2.getReason())) {
            return ResultUtil.error("扣分原因不能为空");
        }
        if (StringUtils.isEmpty(due2.getId())) {
            return ResultUtil.error("id不能为空");
        }

        Example example = new Example(Due2.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", due2.getId());
        due2Mapper.updateByExampleSelective(due2, example);
        return ResultUtil.success();
    }
}
