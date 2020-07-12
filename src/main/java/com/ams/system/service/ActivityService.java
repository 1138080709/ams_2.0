package com.ams.system.service;

import com.ams.common.utils.ResultBean;
import com.ams.system.dao.ActivityInfoMapper;
import com.ams.system.dao.ActivityParticipatorInfoMapper;
import com.ams.system.entity.ActivityInfo;
import com.ams.system.entity.ActivityParticipatorInfo;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: WuWeiquan
 * @Date: 2020/5/28 17:39
 */
@Service
public class ActivityService {

    @Resource
    private ActivityInfoMapper activityInfoDao;

    @Resource
    private ActivityParticipatorInfoMapper activityParticipatorInfoDao;

    @Transactional
    public Integer add(ActivityInfo activityInfo) {
        judgeActivityFlag(activityInfo);
        activityInfoDao.insert(activityInfo);
        return activityInfo.getActivityId();
    }

    @Transactional
    public void delete(Integer activityId) {
        activityInfoDao.deleteByPrimaryKey(activityId);
        activityParticipatorInfoDao.deleteByActivityId(activityId);
    }

    @Transactional
    public void edit(ActivityInfo activityInfo) {
        judgeActivityFlag(activityInfo);
        activityInfoDao.updateByPrimaryKey(activityInfo);
    }

    /**
     * 判断活动状态
     */
    public void judgeActivityFlag(ActivityInfo activityInfo) {
        Date currentTime = new Date(System.currentTimeMillis());
        if (currentTime.before(activityInfo.getStartTime())) {
            activityInfo.setActivityFlag(0);
        } else if (currentTime.after(activityInfo.getStartTime()) && currentTime.before(activityInfo.getOverTime())) {
            activityInfo.setActivityFlag(1);
        } else if (currentTime.after(activityInfo.getOverTime())) {
            activityInfo.setActivityFlag(2);
        }
    }

    public ActivityInfo selectByActivityId(Integer activityId) {
        return activityInfoDao.selectByPrimaryKey(activityId);
    }

    public List<ActivityInfo> selectAllWithQuery(int page, int limit, ActivityInfo activityInfoQuery) {
        PageHelper.startPage(page,limit);
        return activityInfoDao.selectAllWithQuery(activityInfoQuery);
    }

    public List<ActivityInfo> selectAll() {
        return activityInfoDao.selectAll();
    }

    public Integer apply(ActivityParticipatorInfo activityParticipatorInfo) {
        return activityParticipatorInfoDao.insert(activityParticipatorInfo);
    }

    public void applyEdit(ActivityParticipatorInfo activityParticipatorInfo) {
        activityParticipatorInfoDao.updateByPrimaryKey(activityParticipatorInfo);
    }

    public void applyDel(Integer participatorId) {
        activityParticipatorInfoDao.deleteByPrimaryKey(participatorId);
    }

    public List<ActivityParticipatorInfo> applyInfoListById(Integer activityId) {
        return activityParticipatorInfoDao.selectAllByActivityId(activityId);
    }

    public ActivityInfo getActivityInfo(Integer activityId) {
        return activityInfoDao.selectByPrimaryKey(activityId);
    }
}
