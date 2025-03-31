package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 审核基础类
 * @Author: huangzizhu
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditBase {
    private Long id; // 审核ID（唯一标识）
    private Integer status; // 审核状态（1待审核、2已通过、3已拒绝）
    private LocalDateTime auditTime; // 审核时间
    private Long auditorId; // 审核人ID（逻辑外键，关联到管理员表）
    private LocalDateTime approvalTime; // 审核通过时间
    private String rejectionReason; // 审核拒绝原因
    private String modificationSuggestions; // 审核修改建议
    private Integer priority; // 审核优先级（1高、2中、3低
    private Long userId; // 用户ID（逻辑外键，关联到用户表）
}