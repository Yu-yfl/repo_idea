package com.lagou.dao;

import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;

import java.util.List;

public interface PromotionAdMapper {

    /**
     * 分页查询广告信息
     */
    public List<PromotionAd> findAllPromotionAdByPage();

    /**
     * 广告动态上下限
     */
    public void updatePromotionAd(PromotionAd promotionAd);

}
