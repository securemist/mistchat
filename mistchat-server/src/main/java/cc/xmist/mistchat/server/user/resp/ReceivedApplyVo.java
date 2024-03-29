package cc.xmist.mistchat.server.user.model.resp;

import cc.xmist.mistchat.server.common.enums.ApplyStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReceivedApplyVo {
    private Long applyId;
    @Schema(description = "申请人")
    private Long applyUserId;
    @Schema(description = "申请状态")
    private ApplyStatus status;
    @Schema(description = "申请信息")
    private String msg;
}
