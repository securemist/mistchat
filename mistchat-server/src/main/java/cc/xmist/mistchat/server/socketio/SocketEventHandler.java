package cc.xmist.mistchat.server.socketio;

import cc.xmist.mistchat.server.common.exception.BusinessException;
import cc.xmist.mistchat.server.socketio.model.LoginRequest;
import cc.xmist.mistchat.server.socketio.model.SocketResponse;
import cc.xmist.mistchat.server.socketio.model.SocketResponseType;
import cc.xmist.mistchat.server.user.entity.User;
import cc.xmist.mistchat.server.user.service.AuthService;
import cc.xmist.mistchat.server.user.service.UserService;
import cn.hutool.core.util.StrUtil;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class SocketEventHandler {

    @Resource
    private AuthService authService;

    @Resource
    private UserService userService;

    private ConcurrentHashMap<UUID, String> sessionIdMap = new ConcurrentHashMap<>();

    /**
     * 建立连接时获取token并验证
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {

    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
    }


    @OnEvent(value = "verifyToken")
    public void onTokenEvent(SocketIOClient client, AckRequest request, String token) {
        Long uid = authService.verify(token);
        if (uid == null) {
            request.sendAckData(SocketResponse.build(SocketResponseType.INVALIDATE_TOKEN, null));
            return;
        }

        // token续期
        authService.renewalToken(token);
        request.sendAckData(SocketResponse.build(SocketResponseType.LOGIN_SUCCESS, token));
    }


    /**
     * 专门的登陆请求
     */
    @OnEvent(value = "login")
    public void onLoginEvent(SocketIOClient client, AckRequest request, LoginRequest loginRequest) {
        User user = null;
        try {
            user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        } catch (BusinessException e) {
            request.sendAckData(SocketResponse.build(SocketResponseType.LOGIN_FAILED, null));
            return;
        }

        // 签发token
        String token = authService.login(user.getId());
        request.sendAckData(SocketResponse.build(SocketResponseType.LOGIN_SUCCESS, token));
    }
}