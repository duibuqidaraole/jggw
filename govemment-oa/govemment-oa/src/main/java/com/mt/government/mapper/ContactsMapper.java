package com.mt.government.mapper;

import com.mt.government.model.Contacts;
import com.mt.government.common.config.MyMapper;

import java.util.List;

public interface ContactsMapper extends MyMapper<Contacts> {
    /**
     * 导出通讯录列表
     * @return
     */
    List<Contacts> selectAllContacts();
}