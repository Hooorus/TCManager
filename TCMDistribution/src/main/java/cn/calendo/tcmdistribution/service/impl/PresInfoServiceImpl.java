package cn.calendo.tcmdistribution.service.impl;

import cn.calendo.tcmdistribution.dao.PresInfoDao;
import cn.calendo.tcmdistribution.dto.RcvPresInfoDTO;
import cn.calendo.tcmdistribution.dto.RmvPresInfoDTO;
import cn.calendo.tcmdistribution.entity.PresInfo;
import cn.calendo.tcmdistribution.service.IPresInfoService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PresInfoServiceImpl extends ServiceImpl<PresInfoDao, PresInfo> implements IPresInfoService {

    /////////////////////////////////////////////审核/////////////////////////////////////////////

    @Override
    public boolean checkPresInfo(RcvPresInfoDTO rcvPresInfoDTO) {
        if (rcvPresInfoDTO == null ||
                rcvPresInfoDTO.getId() == null ||
                rcvPresInfoDTO.getPrice() == null ||
                rcvPresInfoDTO.getDoctorId() == null ||
                rcvPresInfoDTO.getUnit() == null ||
                rcvPresInfoDTO.getTransactionDate() == null ||
                rcvPresInfoDTO.getTransactionTime() == null ||
                rcvPresInfoDTO.getOutpatientNo() == null) {
            return false;
        }
        return true;
    }

    /////////////////////////////////////////////增加/////////////////////////////////////////////

    @Override
    public boolean saveRcvPresInfoDTO2PresInfoById(RcvPresInfoDTO rcvPresInfoDTO) {
        PresInfo presInfo = new PresInfo();
        BeanUtil.copyProperties(rcvPresInfoDTO, presInfo, "id");//DTO转储到实体类中
        presInfo.setIsDistri(0);
        presInfo.setIsDeleted(0);
        save(presInfo);//在DB中存入此对象
        return true;
    }

    /////////////////////////////////////////////查询/////////////////////////////////////////////

    @Override
    public List<PresInfo> queryPresInfoAll() {
        LambdaQueryWrapper<PresInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PresInfo::getIsDeleted, 0);//条件为未删除
        lqw.orderByDesc(PresInfo::getTransactionDate);//按日期降序排列
        lqw.orderByDesc(PresInfo::getTransactionTime);//按时间降序排列
        return list();
    }

    @Override
    public PresInfo queryPresInfoById(Long id) {
        LambdaQueryWrapper<PresInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PresInfo::getIsDeleted, 0);//条件为未删除
        lqw.orderByDesc(PresInfo::getTransactionDate);//按日期降序排列
        lqw.orderByDesc(PresInfo::getTransactionTime);//按时间降序排列
        return getOne(lqw);
    }

    /////////////////////////////////////////////移除/////////////////////////////////////////////

    @Override
    public boolean removePresInfoById(RmvPresInfoDTO rmvPresInfoDTO) {
        LambdaQueryWrapper<PresInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PresInfo::getId, rmvPresInfoDTO.getId());//条件为获取到的dto的id
        rmvPresInfoDTO.setIsDeleted(1);//设置isDeleted为1
        return update(rmvPresInfoDTO, lqw);//被设置更新后的实体类对象和查询条件
    }

    @Override
    public boolean foreverRemovePresInfoById(Long id) {
        return false;
    }

    /////////////////////////////////////////////恢复/////////////////////////////////////////////

    @Override
    public boolean repentPresInfoById(RmvPresInfoDTO rmvPresInfoDTO) {
        LambdaQueryWrapper<PresInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PresInfo::getId, rmvPresInfoDTO.getId());//条件为获取到的dto的id
        rmvPresInfoDTO.setIsDeleted(0);//设置isDeleted为0
        return update(rmvPresInfoDTO, lqw);//被设置更新后的实体类对象和查询条件
    }

}