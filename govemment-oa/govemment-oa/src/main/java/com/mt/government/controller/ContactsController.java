package com.mt.government.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.mt.government.mapper.ContactsMapper;
import com.mt.government.mapper.UserMapper;
import com.mt.government.model.Contacts;
import com.mt.government.model.User;
import com.mt.government.service.ContactsService;
import com.mt.government.utils.MyExcelUtil;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 联系人控制层
 *
 * @author fuzhigang
 * @date 2019-10-16
 */
@CrossOrigin
@RestController
@RequestMapping("/contacts")
public class ContactsController {
    @Autowired
    private ContactsService contactsService;

    @Autowired
    private ContactsMapper contactsMapper;

    @Autowired
    private UserMapper userMapper;


    /**
     * 查询单个联系人信息
     *
     * @param id 联系人id
     * @return 查询结果
     */
    @GetMapping("/findOne")
    public Result findOne(Integer id) {
        if (id == null || id == 0) {
            return ResultUtil.error("error");
        }
        return ResultUtil.success(contactsService.findOne(id));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll() {
        return ResultUtil.success(contactsService.findAll());
    }

    /**
     * 根据单位分页查询
     *
     * @param page     当前页
     * @param pageSize 每页数量
     * @param userId   单位id
     * @param linkman  联系人名称
     * @return 分页结果
     */
    @GetMapping("/list")
    public Result findByUserId(Integer page, Integer pageSize, String userId, @RequestParam(defaultValue = "") String linkman) {
        return ResultUtil.success(contactsService.findByUserId(page, pageSize, userId, linkman));
    }

    /**
     * 添加联系人
     *
     * @param contacts 联系人信息
     * @return 添加条数
     */
    @PostMapping("/add")
    public Result add(@RequestBody Contacts contacts) {
        if (StrUtil.isEmpty(contacts.getLinkman())) {
            return ResultUtil.error("联系人名称不能为空");
        }
        return ResultUtil.success(contactsService.add(contacts));
    }

    /**
     * 更新联系人
     *
     * @param contacts
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody Contacts contacts) {
        if (StrUtil.isEmpty(contacts.getLinkman())) {
            return ResultUtil.error("联系人名称不能为空");
        }
        return ResultUtil.success(contactsService.update(contacts));
    }

    @GetMapping("/delete")
    public Result delete(Integer id) {
        if (id == null || id == 0) {
            return ResultUtil.error("error");
        }
        return ResultUtil.success(contactsService.delete(id));
    }


    /**
     * 导出通讯录信息
     *
     * @param request
     * @return
     */
    @GetMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 查询出需要导出的list数据
        List<Contacts> list = contactsMapper.selectAllContacts();

        String headArr[] = {"linkman", "position", "gender", "telephone", "address", "onWorkTime", "orgName"};
        String headArrAlias[] = {"姓名", "职务", "性别", "手机", "办公室", "任现党内职务时间", "所属组织机构"};
//        // 将list导出到excel表格中
        MyExcelUtil.getExcel(response, list, "通讯录信息.xls", headArr, headArrAlias);
    }


    /**
     * 通讯录信息导入
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/import")
    public Result importExcel(MultipartFile file) throws Exception {

        if (StringUtils.isEmpty(file)) {
            return ResultUtil.error("file不能为空");
        }

        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        int cnt = reader.readAll().size();
        if (reader.readAll().size() > 0) { // excel中存在数据
//            List<Map<String, Object>> read = reader.read(1, 2, 2147483647);
            List<Map<String, Object>> read = reader.readAll();
            for (int i = 0; i < read.size(); i++) {

                Contacts contacts = new Contacts();
                if (!StringUtils.isEmpty(read.get(i).get("姓名"))) {
                    contacts.setLinkman(read.get(i).get("姓名").toString());
                }
                if (!StringUtils.isEmpty(read.get(i).get("职务"))) {
                    contacts.setPosition(read.get(i).get("职务").toString());
                }
                if (!StringUtils.isEmpty(read.get(i).get("性别"))) {
                    contacts.setGender(Integer.parseInt(read.get(i).get("性别").toString()));
                }

                if (StringUtils.isEmpty(read.get(i).get("手机"))) {
                    return ResultUtil.error("第" + i + "行，手机不能为空");
                }
                String telephone = read.get(i).get("手机").toString();
                contacts.setTelephone(telephone);

                if (!StringUtils.isEmpty(read.get(i).get("办公室"))) {
                    contacts.setAddress(read.get(i).get("办公室").toString());
                }
                if (!StringUtils.isEmpty(read.get(i).get("任现党内职务时间"))) {
                    contacts.setOnWorkTime(read.get(i).get("任现党内职务时间").toString());
                }
                if (StringUtils.isEmpty(read.get(i).get("所属组织机构"))) {
                    return ResultUtil.error("第" + i + "行，所属组织机构不能为空");
                }
                // 组织机构名称
                String orgName = read.get(i).get("所属组织机构").toString();
                // 获取到对应的userId
                User user = new User();
                user.setOrgName(orgName);
                User user1 = userMapper.selectOne(user);
                String userId = user1.getUserId();
                if (StringUtils.isEmpty(userId)) { // 为空
                    return ResultUtil.error("该组织机构对应的用户id不存在，请确认数据重新导入！！！");
                }
                // 将userId设置进Contacts对象中去
                contacts.setUserId(userId);

                // 设置新增时间和修改时间
                contacts.setCreateTime(new Date());
                contacts.setUpdateTime(new Date());

                int insert = contactsMapper.insert(contacts);
                if (insert < 0) { //
                    return ResultUtil.error("新增失败！！！");
                }
            }
            return ResultUtil.success();

        }
        return ResultUtil.error("excle表格不为空!");
    }

    /**
     * 添加扣分记录
     *
     * @param contacts
     * @return
     */
    @PostMapping("/addReason")
    public Result addReason(@RequestBody Contacts contacts) {
        if (StringUtils.isEmpty(contacts.getPoint())) {
            return ResultUtil.error("分值不能为空");
        }
        if (StringUtils.isEmpty(contacts.getReason())) {
            return ResultUtil.error("请填写扣分原因");
        }
        if (StringUtils.isEmpty(contacts.getId())) {
            return ResultUtil.error("id不能为空");
        }
        Example example = new Example(Contacts.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", contacts.getId());
        contactsMapper.updateByExampleSelective(contacts, example);
        return ResultUtil.success();
    }


}
