package com.ams.system.service;

import com.ams.system.dao.RoleOperatorMapper;
import com.ams.system.dao.MenuMapper;
import com.ams.system.dao.OperatorMapper;
import com.ams.system.entity.Operator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OperatorService {
    
    @Resource
    private OperatorMapper operatorDao;
    
    @Resource
    private MenuMapper menuDao;
    
    @Resource
    private RoleOperatorMapper jobOperatorDao;
    
    public List<Operator> selectAll() {
        return operatorDao.selectAll();
    }

    public Integer add(Operator operator) {
        operatorDao.insert(operator);
        return operator.getOperatorId();
    }

    public void edit(Operator operator) {
        operatorDao.updateByPrimaryKey(operator);
    }

    public void delete(Integer operatorId) {
        operatorDao.deleteByPrimaryKey(operatorId);
        jobOperatorDao.deleteRoleOperatorByOperatorId(operatorId);
    }

    public Operator selectOperatorByOperatorId(Integer operatorId) {
        return operatorDao.selectByPrimaryKey(operatorId);
    }

    public List<Operator> selectByMenuId(Integer menuId) {
        return operatorDao.selectByMenuId(menuId);
    }
}
