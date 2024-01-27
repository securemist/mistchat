package cc.xmist.mistchat.server.user.model.entity;

import cc.xmist.mistchat.server.user.model.enums.ApplyStatusType;
import cc.xmist.mistchat.server.user.model.enums.ApplyType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户申请表
 * </p>
 *
 * @author securemist
 * @since 2024-01-27
 */
@Getter
@Setter
@Builder
@TableName("user_apply")
public class UserApply implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 申请人uid
     */
    @TableField("uid")
    private Long uid;

    /**
     * 申请类型 加好友与加群聊，退出群聊
     */
    @TableField("type")
    private ApplyType type;

    /**
     * 接收人uid
     */
    @TableField("target_id")
    private Long targetId;

    /**
     * 申请信息
     */
    @TableField("msg")
    private String msg;

    /**
     * 申请状态 拒绝 待审批 同意
     */
    @TableField("status")
    private ApplyStatusType status;

    /**
     * 阅读时间
     */
    @TableField("read_time")
    private LocalDateTime readTime;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
}
