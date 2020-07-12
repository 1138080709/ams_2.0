package com.ams.system.service;

import com.ams.common.exception.NoFillStudentInfoException;
import com.ams.system.dao.CashMapper;
import com.ams.system.dao.MaterialFlowInfoMapper;
import com.ams.system.dao.MaterialMapper;
import com.ams.system.entity.Cash;
import com.ams.system.entity.Material;
import com.ams.system.entity.MaterialFlowInfo;
import com.ams.system.entity.User;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WuWeiquan
 * @Date: 2020/6/7 22:58
 */
@Service
public class FinanceService {

    @Resource
    private CashMapper cashDao;

    @Resource
    private MaterialMapper materialDao;

    @Resource
    private MaterialFlowInfoMapper materialFlowInfoDao;

    public List<Cash> selectCashsWithQuery(int page, int limit, Cash cashQuery) {
        PageHelper.startPage(page, limit);
        return cashDao.selectAllWithQuery(cashQuery);
    }

    public Integer addCash(Cash cash) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user.getStudentId() == null)
            throw new NoFillStudentInfoException();
        cash.setProposerId(user.getStudentId());
        return cashDao.insert(cash);
    }

    public void editCash(Cash cash) {
        cashDao.updateByPrimaryKey(cash);
    }

    public void delCash(Integer cashId) {
        cashDao.deleteByPrimaryKey(cashId);
    }

    public List<Material> selectMaterialsWithQuery(int page, int limit, Material materialQuery) {
        PageHelper.startPage(page, limit);
        return materialDao.selectAllWithQuery(materialQuery);
    }

    public List<Material> selectMaterials() {
        return materialDao.selectAll();
    }

    public Integer addMaterial(Material material) {
        return materialDao.insert(material);
    }

    public void delMaterial(Integer materialId) {
        materialDao.deleteByPrimaryKey(materialId);
    }

    public void editMaterial(Material material) {
        materialDao.updateByPrimaryKey(material);
    }

    public List<MaterialFlowInfo> selectMaterialFlowInfosWithQuery(int page, int limit, MaterialFlowInfo materialFlowInfoQuery) {
        PageHelper.startPage(page, limit);
        return materialFlowInfoDao.selectAllWithQuery(materialFlowInfoQuery);
    }

    public Integer addMaterialFlowInfo(MaterialFlowInfo materialFlowInfo) {
        return materialFlowInfoDao.insert(materialFlowInfo);
    }

    public void delMaterialFlowInfo(Integer materialFlowId) {
        materialFlowInfoDao.deleteByPrimaryKey(materialFlowId);
    }
    
    public void editMaterialFlowInfo(MaterialFlowInfo materialFlowInfo) {
        materialFlowInfoDao.updateByPrimaryKey(materialFlowInfo);
    }

    public Cash selectCashById(Integer cashId) {
        return cashDao.selectByPrimaryKey(cashId);
    }

    public Material selectMaterialById(Integer materialId) {
        return materialDao.selectByPrimaryKey(materialId);
    }

    public MaterialFlowInfo selectMaterialFlowInfoById(Integer materialFlowInfoId) {
        return materialFlowInfoDao.selectByPrimaryKey(materialFlowInfoId);
    }

    public List<Cash> selectCashAuditsWithQuery(int page, int limit, Cash cashQuery) {
        PageHelper.startPage(page, limit);
        return cashDao.selectCashAuditsWithQuery(cashQuery);
    }

    public List<Cash> selectCashExecutesWithQuery(int page, int limit, Cash cashQuery) {
        PageHelper.startPage(page, limit);
        return cashDao.selectCashExecutesWithQuery(cashQuery);
    }

    public List<MaterialFlowInfo> selectMaterialFlowInfoAuditsWithQuery(int page, int limit, MaterialFlowInfo
            materialFlowInfoQuery) {
        PageHelper.startPage(page, limit);
        return materialFlowInfoDao.selectMaterialFlowInfoAuditsWithQuery(materialFlowInfoQuery);
    }

    @Transactional
    public void auditMaterialFlowInfo(MaterialFlowInfo materialFlowInfo) {
        if (materialFlowInfo.getAuditFlag() == 1) {
            if (materialFlowInfo.getInfoType() == 0) {
                if (materialFlowInfo.getMaterialId() != null && materialFlowInfo.getNumber() != null) {
                    Material material = materialDao.selectByPrimaryKey(materialFlowInfo.getMaterialId());
                    material.setRemainNumber(material.getTotalNumber() - materialFlowInfo.getNumber());
                    materialDao.updateByPrimaryKey(material);
                }
            } else if (materialFlowInfo.getInfoType() == 1) {
                if (materialFlowInfo.getMaterialId() != null && materialFlowInfo.getNumber() != null) {
                    Material material = materialDao.selectByPrimaryKey(materialFlowInfo.getMaterialId());
                    material.setRemainNumber(material.getRemainNumber() + materialFlowInfo.getNumber());
                    materialDao.updateByPrimaryKey(material);
                }
            }
        }
        materialFlowInfoDao.updateByPrimaryKey(materialFlowInfo);
    }
    
}
