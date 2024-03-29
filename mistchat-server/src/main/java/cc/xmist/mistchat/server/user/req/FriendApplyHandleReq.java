package cc.xmist.mistchat.server.user.model.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FriendApplyHandleReq {
    private Long applyId;
    @NotNull
    private Boolean pass;
    private String msg;
}
