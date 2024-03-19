package cc.xmist.mistchat.server.group.sevrice;

import cc.xmist.mistchat.server.chat.dao.ContactDao;
import cc.xmist.mistchat.server.chat.service.ContactService;
import cc.xmist.mistchat.server.common.event.GroupAddEvent;
import cc.xmist.mistchat.server.common.util.CursorResult;
import cc.xmist.mistchat.server.group.dao.GroupApplyDao;
import cc.xmist.mistchat.server.group.dao.GroupDao;
import cc.xmist.mistchat.server.group.dao.GroupMemberDao;
import cc.xmist.mistchat.server.group.entity.Group;
import cc.xmist.mistchat.server.group.entity.GroupMember;
import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupMemberDao groupMemberDao;
    private final GroupDao groupDao;
    private final GroupApplyDao groupApplyDao;
    private final ContactService contactService;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 获取群聊所有成员
     *
     * @param groupId
     * @param cursor
     * @param pageSize
     * @return
     */
    public CursorResult<Long> getMembersCursorabler(Long groupId, String cursor, Integer pageSize) {
        List<GroupMember> data = groupMemberDao.getMembersCursorable(groupId, cursor, pageSize);

        List<Long> uids = data.stream().map(GroupMember::getUid).collect(Collectors.toList());

        Boolean isLast = false;
        String newCursor = null;
        if (data.size() != pageSize || CollectionUtil.isEmpty(data)) {
            isLast = true;
            newCursor = null;
        } else {
            Long last = CollectionUtil.getLast(data).getId();
            newCursor = last == null ? null : last.toString();
        }
        return new CursorResult<Long>(newCursor, isLast, uids);
    }


    /**
     * 创建群聊
     *
     * @param createrId
     * @param name
     * @param uidList
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Long create(Long createrId, String name, List<Long> uids) {
        Group group = groupDao.create(createrId, name);
        Long groupId = group.getId();

        // 群成员算上群主
        if(uids.indexOf(createrId) == -1) uids.add(createrId);
        groupMemberDao.initMembers(groupId,uids);

        // 初始化群成员会话信息
        contactService.initGroup(groupId,uids);

        eventPublisher.publishEvent(new GroupAddEvent(this, group, uids));
        return groupId;
    }
}

