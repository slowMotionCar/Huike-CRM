package com.huike.business.service;

import com.huike.business.domain.TbBusiness;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.business.domain.dto.TbBusinessAssingDTO;
import com.huike.business.domain.dto.TbBusinessDTODTO;
import com.huike.business.domain.dto.TbBusinessGainDTO;
import com.huike.common.core.page.TableDataInfo;

import java.util.List;

/**
* @author 93238
* @description 针对表【tb_business(商机)】的数据库操作Service
* @createDate 2023-10-12 06:08:37
*/
public interface TbBusinessService extends IService<TbBusiness> {

    void insertBusiness(TbBusinessDTODTO tbBusinessDTODTO);

    void updateBusiness(TbBusinessDTODTO tbBusinessDTODTO);

    void assignBusiness(TbBusinessAssingDTO tbBusinessAssingDTO);

    void deleteBusinessByIds(String ids);

    void backPoll(Integer id, Long reason);

    TbBusiness selectBusinessDetail(Integer id);

    List<TbBusiness> pageSelectPool(TbBusinessDTODTO tbBusinessDTODTO);

    List<TbBusiness> pageSelectBusiness(TbBusinessDTODTO tbBusinessDTODTO);

    void gain(TbBusinessGainDTO tbBusinessGainDTO);
}
