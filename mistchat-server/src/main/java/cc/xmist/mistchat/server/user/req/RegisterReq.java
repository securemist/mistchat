package cc.xmist.mistchat.server.user.model.req;

import lombok.Data;

@Data
public class RegisterReq {
    private String username;
    private String password;
    private String name;
}
