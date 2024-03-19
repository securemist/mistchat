package cc.xmist.mistchat.server.chat.dao;

import cc.xmist.mistchat.server.chat.entity.Contact;
import cc.xmist.mistchat.server.chat.mapper.ContactMapper;
import cc.xmist.mistchat.server.common.enums.ChatType;
import cc.xmist.mistchat.server.common.exception.IlleglaException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactDao extends ServiceImpl<ContactMapper, Contact> {

    /**
     * 用户发送消息的会话更新
     *
     * @param contactId
     * @param msgId
     */
    public void updateSending(Long contactId, Long msgId) {
        lambdaUpdate()
                .set(Contact::getLastMsgId, msgId)
                .eq(Contact::getId, contactId);
    }


    /**
     * 用户读取消息的会话更新
     * 某一个用户在某一个会话读取某个消息，如果会话的实际 uid 与请求的 uid 不符，属于非法操作，操作别人的接口
     *
     * @param uid       请求中的 uid
     * @param contactId 用户读消息时自己所处的 contactId，不是消息发送者 contactId
     * @param msgId     消息 id
     */
    public void updateReading(Long uid, Long contactId, Long msgId) {
        boolean ok = lambdaUpdate()
                .set(Contact::getReadMsgId, msgId)
                .eq(Contact::getUid, uid)
                .eq(Contact::getId, contactId)
                .update();
        if (!ok) throw new IlleglaException();
    }


    /**
     * 列出用户所有的会话
     *
     * @param uid
     * @return
     */
    public List<Contact> listByUid(Long uid) {
        return lambdaQuery()
                .eq(Contact::getUid, uid)
                .list();
    }


    /**
     * 两个用户成为好友，创建会话
     *
     * @param uid1
     * @param uid2
     * @return uid <-> 两个人的会话 id
     */
    public List<Long> initFriendContact(Long uid1, Long uid2) {
        Contact c1 = Contact.builder()
                .uid(uid1)
                .chatId(uid2)
                .chatType(ChatType.FRIEND)
                .build();

        Contact c2 = Contact.builder()
                .uid(uid2)
                .chatId(uid1)
                .chatType(ChatType.FRIEND)
                .build();

        saveBatch(Arrays.asList(c1, c2));

        return Arrays.asList(c1.getId(),c2.getId());
    }


    /**
     * 创建群聊时，初始话所有成员的会话信息
     *
     * @param groupId
     * @param uids
     * @return
     */
    public List<Long> initGroupContacts(Long groupId, List<Long> uids) {
        List<Contact> contacts = uids.stream().map(uid -> {
            return Contact.builder()
                    .uid(uid)
                    .chatId(groupId)
                    .chatType(ChatType.GROUP)
                    .build();
        }).collect(Collectors.toList());

        saveBatch(contacts);

        List<Long> list = contacts.stream()
                .map(Contact::getId)
                .collect(Collectors.toList());
        return list;
    }

    /**
     * 添加好友或加入群聊，发完系统问候语后，将 msgId 设置为 last_msg_id
     *
     * @param contactIds
     * @param msgIds
     */
    public void initLastMsgId(List<Long> contactIds, List<Long> msgIds) {
        for (int i = 0; i < contactIds.size(); i++) {
            lambdaUpdate()
                    .set(Contact::getLastMsgId, msgIds.get(i))
                    .eq(Contact::getId, contactIds.get(i))
                    .update();
        }
    }
}
