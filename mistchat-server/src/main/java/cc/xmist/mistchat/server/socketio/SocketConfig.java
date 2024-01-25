package cc.xmist.mistchat.server.socketio;

import cc.xmist.mistchat.server.user.service.AuthService;
import cc.xmist.mistchat.server.user.service.UserService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.corundumstudio.socketio.AuthorizationResult;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class SocketConfig {

    @Value("${ws.port}")
    private String port;
    @Resource
    AuthService authService;
    @Resource
    UserService userService;

    @Bean
    public SocketIOServer server() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("localhost");
        config.setPort(Integer.parseInt(port));
        // 用于身份验证
        //  http://localhost:8081?token=xxxxxxx可以获取token
        config.setAuthorizationListener(data -> {
            String hostName = data.getAddress().getHostName();
            String token = data.getSingleUrlParam("token");
            if (StrUtil.isNotBlank(token)) {
                log.info("登陆token：{}", token);
            }
            return new AuthorizationResult(true);
        });

        return new SocketIOServer(config);
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer server) {
        return new SpringAnnotationScanner(server);
    }
}
