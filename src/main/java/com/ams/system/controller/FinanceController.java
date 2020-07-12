package com.ams.system.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.ams.common.utils.PageResultBean;
import com.ams.common.utils.ResultBean;
import com.ams.common.validator.Create;
import com.ams.common.validator.Update;
import com.ams.system.entity.*;
import com.ams.system.service.FinanceService;
import com.ams.system.service.StudentService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: WuWeiquan
 * @Date: 2020/5/28 18:18
 */
@Controller
@RequestMapping("/finance")
public class FinanceController {

    @Resource
    private FinanceService financeService;

    @Resource
    private StudentService studentService;
    
    private static final Logger log = LoggerFactory.getLogger(FinanceController.class);

    @GetMapping("/cash/index")
    public String cashIndex() {
        return "/cash/cash-list";
    }

    @GetMapping("/cash/add")
    public String cashAdd() {
        return "/cash/cash-add";
    }

    @GetMapping("/cash/edit/{cashId}")
    public String cashEdit(Model model, @PathVariable("cashId") Integer cashId) {
        Cash cash = financeService.selectCashById(cashId);
        model.addAttribute("cash", cash);
        return "/cash/cash-add";
    }

    @GetMapping("/cash/apply/index")
    public String cashApplyIndex(Model model) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Student student = studentService.selectStudentByStudentId(user.getStudentId());
        model.addAttribute("student", student);
        return "/cash/cash-apply";
    }

    @GetMapping("/cash/audit/index")
    public String cashAuditIndex(Model model) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("auditorId", user.getStudentId());
        return "/cash/cash-audit";
    }

    @GetMapping("/cash/execute/index")
    public String cashExecuteIndex() {
        return "/cash/cash-execute";
    }

    @GetMapping("/material/index")
    public String materialIndex() {
        return "/material/material-list";
    }

    @GetMapping("/material/add")
    public String materialAdd() {
        return "/material/material-add";
    }

    @GetMapping("/material/edit/{materialId}")
    public String materialEdit(Model model, @PathVariable("materialId") Integer materialId) {
        Material material = financeService.selectMaterialById(materialId);
        model.addAttribute("material", material);
        return "/material/material-add";
    }

    @GetMapping("/material/flow/index")
    public String materialFlowIndex() {
        return "/material/materialFlowInfo-list";
    }

    @GetMapping("/material/flow/add")
    public String materialFlowAdd(Model model) {
        model.addAttribute("materials", financeService.selectMaterials());
        return "/material/materialFlowInfo-add";
    }

    @GetMapping("/material/flow/edit/{materialFlowInfoId}")
    public String materialFlowEdit(Model model, @PathVariable("materialFlowInfoId") Integer materialFlowInfoId) {
        model.addAttribute("materialFlowInfo", financeService.selectMaterialFlowInfoById(materialFlowInfoId));
        model.addAttribute("materials", financeService.selectMaterials());
        return "/material/materialFlowInfo-add";
    }

    @GetMapping("/material/flow/audit/index")
    public String materialAuditIndex(Model model) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("auditorId", user.getStudentId());
        return "/material/materialFlowInfo-audit";
    }

    @ApiOperation("增加资金流动信息")
    @PostMapping("/cash")
    @ResponseBody
    public ResultBean addCash(@Validated(Create.class) Cash cash) {
        return ResultBean.success(financeService.addCash(cash));
    }

    @ApiOperation("编辑资金流动信息")
    @PutMapping("/cash")
    @ResponseBody
    public ResultBean editCash(@Validated(Update.class) Cash cash) {
        financeService.editCash(cash);
        return ResultBean.success();
    }

    @ApiOperation("删除资金流动信息")
    @DeleteMapping("/cash/{cashId}")
    @ResponseBody
    public ResultBean delCash(@PathVariable("cashId") Integer cashId) {
        financeService.delCash(cashId);
        return ResultBean.success();
    }

    @ApiOperation("获取资金流动信息列表")
    @GetMapping("/cash/list")
    @ResponseBody
    public PageResultBean<Cash> getCashList(@RequestParam(value = "page", defaultValue = "1") int page,
                                            @RequestParam(value = "limit", defaultValue = "10") int limit,
                                            Cash cashQuery) {
        List<Cash> cash = financeService.selectCashsWithQuery(page, limit, cashQuery);
        PageInfo<Cash> cashPageInfo = new PageInfo<>(cash);
        return new PageResultBean<>(cashPageInfo.getTotal(), cashPageInfo.getList());
    }

    @ApiOperation("获取待审核资金流动信息列表")
    @GetMapping("/cash/audit/list")
    @ResponseBody
    public PageResultBean<Cash> getCashAuditList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                 Cash cashQuery) {
        List<Cash> cash = financeService.selectCashAuditsWithQuery(page, limit, cashQuery);
        PageInfo<Cash> cashPageInfo = new PageInfo<>(cash);
        return new PageResultBean<>(cashPageInfo.getTotal(), cashPageInfo.getList());
    }

    @ApiOperation("获取待执行资金流动信息列表")
    @GetMapping("/cash/execute/list")
    @ResponseBody
    public PageResultBean<Cash> getCashExecuteList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                   Cash cashQuery) {

        List<Cash> cash = financeService.selectCashExecutesWithQuery(page, limit, cashQuery);
        PageInfo<Cash> cashPageInfo = new PageInfo<>(cash);
        return new PageResultBean<>(cashPageInfo.getTotal(), cashPageInfo.getList());
    }


    @ApiOperation("获取物资列表")
    @GetMapping("/material/list")
    @ResponseBody
    public PageResultBean<Material> getMaterialList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                    @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                    Material materialQuery) {
        List<Material> materials = financeService.selectMaterialsWithQuery(page, limit, materialQuery);
        PageInfo<Material> materialPageInfo = new PageInfo<>(materials);
        return new PageResultBean<>(materialPageInfo.getTotal(), materialPageInfo.getList());
    }

    @ApiOperation("增加物资信息")
    @PostMapping("/material")
    @ResponseBody
    public ResultBean addMaterial(@Validated(Create.class) Material material) {
        return ResultBean.success(financeService.addMaterial(material));
    }

    @ApiOperation("编辑物资信息")
    @PutMapping("/material")
    @ResponseBody
    public ResultBean editMaterial(@Validated(Update.class) Material material) {
        financeService.editMaterial(material);
        return ResultBean.success();
    }

    @ApiOperation("删除物资信息")
    @DeleteMapping("/material/{materialId}")
    @ResponseBody
    public ResultBean delMaterial(@PathVariable("materialId") Integer materialId) {
        financeService.delMaterial(materialId);
        return ResultBean.success();
    }

    @ApiOperation("根据物资ID获取物资信息")
    @GetMapping("/material/{materialId}")
    @ResponseBody
    public ResultBean getMaterial(@PathVariable("materialId") Integer materialId) {
        return ResultBean.success(financeService.selectMaterialById(materialId));
    }
    

    @ApiOperation("获取物资流动信息")
    @GetMapping("/material/flow/list")
    @ResponseBody
    public PageResultBean<MaterialFlowInfo> getMaterialFlowInfoList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                    @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                                    MaterialFlowInfo materialFlowInfoQuery) {
        List<MaterialFlowInfo> materialFlowInfos = financeService.selectMaterialFlowInfosWithQuery(page, limit, materialFlowInfoQuery);
        PageInfo<MaterialFlowInfo> materialFlowInfoPageInfo = new PageInfo<>(materialFlowInfos);
        return new PageResultBean<>(materialFlowInfoPageInfo.getTotal(), materialFlowInfoPageInfo.getList());
    }

    @ApiOperation("增加物资流动信息")
    @PostMapping("/material/flow")
    @ResponseBody
    public ResultBean addMaterialFlowInfo(@Validated(Create.class) MaterialFlowInfo materialFlowInfo) {
//        log.info(""+materialFlowInfo.getAuditFlag());
        return ResultBean.success(financeService.addMaterialFlowInfo(materialFlowInfo));
    }

    @ApiOperation("编辑物资流动信息")
    @PutMapping("/material/flow")
    @ResponseBody
    public ResultBean editMaterialFlowInfo(@Validated(Update.class) MaterialFlowInfo materialFlowInfo) {
        financeService.editMaterialFlowInfo(materialFlowInfo);
        return ResultBean.success();
    }

    @ApiOperation("删除物资流动信息")
    @DeleteMapping("/material/flow/{materialFlowId}")
    @ResponseBody
    public ResultBean delMaterialFlowInfo(@PathVariable("materialFlowId") Integer materialFlowId) {
        financeService.delMaterialFlowInfo(materialFlowId);
        return ResultBean.success();
    }

    @ApiOperation("获取待审核物资流动信息列表")
    @GetMapping("/material/flow/audit/list")
    @ResponseBody
    public PageResultBean<MaterialFlowInfo> getMaterialFlowAuditList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                     @RequestParam(value = "limit", defaultValue = "10") int limit, 
                                                                     MaterialFlowInfo materialFlowInfoQuery) {
        List<MaterialFlowInfo> materialFlowInfo = financeService.selectMaterialFlowInfoAuditsWithQuery(page, limit, materialFlowInfoQuery);
        PageInfo<MaterialFlowInfo> materialFlowInfoPageInfo = new PageInfo<>(materialFlowInfo);
        return new PageResultBean<>(materialFlowInfoPageInfo.getTotal(), materialFlowInfoPageInfo.getList());
    }
    
    @ApiOperation("审核物资流动信息列表")
    @PutMapping("/material/flow/audit")
    @ResponseBody
    public ResultBean auditMaterialFlowInfo(@Validated(Update.class) MaterialFlowInfo materialFlowInfo) {
        financeService.auditMaterialFlowInfo(materialFlowInfo);
        return ResultBean.success();
    }
    
}
