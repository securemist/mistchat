package cc.xmist.mistchat.server.chat.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author securemist
 * @since 2024-03-14
 */
@TableName("friend_contact")
@Data
@Builder
public class FriendContact implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("uid")
    private Long uid;

    @TableField("friend_uid")
    private Long friendUid;

    /**
     * 最新消息 id
     */
    @TableField("last_msg_id")
    private Long lastMsgId;

    /**
     * 已读到的消息 id
     */
    @TableField("read_msg_id")
    private Long readMsgId;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
