package com.ams.system.controller;

import com.ams.common.utils.PageResultBean;
import com.ams.common.utils.ResultBean;
import com.ams.common.validator.Create;
import com.ams.common.validator.Update;
import com.ams.system.entity.ActivityInfo;
import com.ams.system.entity.ActivityParticipatorInfo;
import com.ams.system.service.ActivityService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WuWeiquan
 * @Date: 2020/5/28 17:38
 */
@Controller
@RequestMapping("/activity")
public class ActivityController {
    
    @Resource
    private ActivityService activityService;
    
    @ApiOperation("新增活动")
    @PostMapping
    @ResponseBody
    public ResultBean add(@Validated(Create.class) ActivityInfo activityInfo) {
        return ResultBean.success(activityService.add(activityInfo));
    }
    
    @ApiOperation("删除活动")
    @DeleteMapping("/{activityId}")
    @ResponseBody
    public ResultBean del(@PathVariable("activityId") Integer activityId) {
        activityService.delete(activityId);
        return ResultBean.success();
    }
    
    @ApiOperation("编辑活动")
    @PutMapping
    @ResponseBody
    public ResultBean edit(@Validated(Update.class) ActivityInfo activityInfo) {
        activityService.edit(activityInfo);
        return ResultBean.success();
    }
    
    @ApiOperation("查询活动")
    @GetMapping("/{activityId}")
    @ResponseBody
    public ResultBean getActivity(@PathVariable("activityId") Integer activityId) {
        return ResultBean.success(activityService.selectByActivityId(activityId));
    }
    
    @ApiOperation("获取活动列表")
    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<ActivityInfo> getList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                ActivityInfo activityInfoQuery) {
        List<ActivityInfo> activityInfos = activityService.selectAllWithQuery(page, limit, activityInfoQuery);
        PageInfo<ActivityInfo> activityInfoPageInfo = new PageInfo<>(activityInfos);
        return new PageResultBean<>(activityInfoPageInfo.getTotal(),activityInfoPageInfo.getList());
    }

    /**
     * 如何实时更新活动状态
     */
    


}
