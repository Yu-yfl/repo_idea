package com.lagou.service;

import com.lagou.domain.Test;

import java.util.List;

public interface TestService {


    /**
     * 对表单进行所有查询
     */
    public List<Test> findAllTest();
}
