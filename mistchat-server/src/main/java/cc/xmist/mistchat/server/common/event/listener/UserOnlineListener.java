package cc.xmist.mistchat.server.common.event.listener;

import cc.xmist.mistchat.server.common.event.UserOnlineEvent;
import cc.xmist.mistchat.server.user.dao.UserDao;
import cc.xmist.mistchat.server.user.model.IpDetail;
import cc.xmist.mistchat.server.user.service.IpService;
import jakarta.annotation.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserOnlineListener {
    @Resource
    UserDao userDao;
    @Resource
    IpService ipService;
    @EventListener(classes = UserOnlineEvent.class)
    public void online(UserOnlineEvent event){
        Long uid = event.getUid();
        userDao.online(uid);
    }

    @EventListener(classes = UserOnlineEvent.class)
    public void parseIpInfo(UserOnlineEvent event) {
        String ip = event.getIp();
        Long uid = event.getUid();
        ipService.updateIpInfo(uid, ip);
    }
}
