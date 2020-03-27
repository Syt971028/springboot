package com.sun.hospital.dao;

import com.sun.hospital.entiy.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 孙耘田
 * @date 2020/3/27 - 12:54
 */
@Mapper
public interface DepartmentMapper {

    @Select("select departname from department")
    List<Department> findAllDepart();
}
