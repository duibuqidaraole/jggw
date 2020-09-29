package com.mt.government.service;

import com.mt.government.model.vo.PagedResult;

public interface TaskUserRelationshipService {

    PagedResult TaskUserList(String userId, Integer status, String title, Integer page, Integer pageSize);
}
