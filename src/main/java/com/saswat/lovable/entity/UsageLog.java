package com.saswat.lovable.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "usage_logs", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "date"}) // one log per user per day
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsageLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDate date;

    private Integer tokensUsed;

}
