package cc.xmist.mistchat.server.chat.model.dao;

import cc.xmist.mistchat.server.chat.model.entity.RoomGroup;
import cc.xmist.mistchat.server.chat.model.mapper.RoomGroupMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author securemist
 * @since 2024-01-26
 */
@Service
public class RoomGroupDao extends ServiceImpl<RoomGroupMapper, RoomGroup> {

    public List<Long> getUsers(Long roomId) {
        return null;
    }

    public RoomGroup getByRoomId(Long roomId) {
        return lambdaQuery()
                .eq(RoomGroup::getRoomId,roomId)
                .one();
    }

}