package cn.calendo.tcmdistribution.controller.shipInfo;

import cn.calendo.tcmdistribution.common.R;
import cn.calendo.tcmdistribution.dto.SndShipInfoDTO;
import cn.calendo.tcmdistribution.entity.ShipInfo;
import cn.calendo.tcmdistribution.service.IShipInfoService;
import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 发送并保存邮政配药报文接口
 */
@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/ship_info/snd")
public class SndShipInfo {

    @Autowired
    private IShipInfoService shipInfoService;

    /**
     * 向邮政发送
     *
     * @param id 报文id
     * @return R对象
     */
    @PostMapping("/normal")
    public R sendMessage2Post(@RequestParam(value = "id") Long id) {
        ShipInfo shipInfo = shipInfoService.queryShipInfoById(id);//查出相应报文实体类
        if (shipInfo == null) {
            return R.error(404, "暂无此报文", new Date());
        }
        SndShipInfoDTO sndShipInfoDTO = new SndShipInfoDTO();
        BeanUtil.copyProperties(shipInfo, sndShipInfoDTO, "isDeleted");//bean拷贝
        boolean res = shipInfoService.sendShipInfo(sndShipInfoDTO);//发送dto
        if (!res) {
            return R.error(404, "发送失败", new Date());
        }
        return R.success(200, "发送成功", new Date());
    }

    /**
     * 向邮政发送自定义信息
     * @param reject_text 自定义信息
     * @return R对象
     */
    @PostMapping("/reject")
    public R sendReject2Post(@RequestParam(value = "reject_text") String reject_text) {
        boolean res = shipInfoService.sendReject(reject_text);
        if (!res) {
            return R.error(404, "发送失败", new Date());
        }
        return R.success(200, "发送成功", new Date());
    }

}