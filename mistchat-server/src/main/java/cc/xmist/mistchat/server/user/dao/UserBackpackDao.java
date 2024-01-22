package cc.xmist.mistchat.server.user.dao;

import cc.xmist.mistchat.server.common.constant.StatusType;
import cc.xmist.mistchat.server.user.entity.UserBackpack;
import cc.xmist.mistchat.server.user.mapper.UserBackpackMapper;
import cc.xmist.mistchat.server.user.model.enums.ItemType;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户背包表 服务实现类
 * </p>
 *
 * @author securemist
 * @since 2024-01-16
 */
@Service
public class UserBackpackDao extends ServiceImpl<UserBackpackMapper, UserBackpack> {

    /**
     * 获取用户的物品数量
     *
     * @param uid      用户id
     * @param itemType 物品枚举
     * @return
     */
    public Long getItemCount(Long uid, ItemType itemType) {
        Long count = lambdaQuery()
                .eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getItemId, itemType.getId())
                .eq(UserBackpack::getStatus, StatusType.NO).count();
        return count;
    }
}