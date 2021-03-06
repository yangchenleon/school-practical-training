package me.maxct.asset.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import me.maxct.asset.constant.AppConst;
import me.maxct.asset.enumerate.PropertyStatus;
import me.maxct.asset.enumerate.TicketStatus;

/**
 * 工单
 * @author imaxct
 * 2019-03-12 10:01
 */
@Data
@Entity
@Table(name = "asset_ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long           id;
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT, timezone = AppConst.TIME_ZONE)
    private LocalDateTime  gmtCreate;
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT, timezone = AppConst.TIME_ZONE)
    private LocalDateTime  gmtModified;
    private Long           applyUserId;
    private Long           curStepId;
    private Long           processId;
    private Long           propertyId;

    @Column(length = 64)
    @Enumerated(EnumType.STRING)
    private TicketStatus   curStatus;

    @Column(length = 250)
    private String         applyReason;

    private Long           depId;

    private Long           transferUserId;

    @Column(length = 64)
    @Enumerated(EnumType.STRING)
    private PropertyStatus initialStatus;

    @Column(length = 64)
    @Enumerated(EnumType.STRING)
    private PropertyStatus finalStatus;
}
