package com.sun.hospital.service.impl;

import com.sun.hospital.dao.DepartmentMapper;
import com.sun.hospital.entiy.Department;
import com.sun.hospital.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 孙耘田
 * @date 2020/3/27 - 12:56
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> findALLDept() {
        List<Department> allDepart = departmentMapper.findAllDepart();
        return allDepart;
    }
}
