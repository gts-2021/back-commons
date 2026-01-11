package com.gts.backcommons.models;


import com.gts.backcommons.enums.AuditAction;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @Enumerated(EnumType.STRING)
    private AuditAction actionType;

    private String entityName;

    private Long entityId;

    @Column(columnDefinition = "TEXT")
    private String oldState;

    @Column(columnDefinition = "TEXT")
    private String newState;

    private LocalDateTime operationDate;
}
