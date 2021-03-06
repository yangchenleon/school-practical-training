package me.maxct.asset.enumerate;

import lombok.Getter;

/**
 * 工单状态
 * @author imaxct
 * 2019-03-12 10:13
 */
@Getter
public enum TicketStatus {
    SUBMIT("SUBMIT", "已提交"),
    PROCESSING("PROCESSING", "审批中"),
    PASS("PASS", "审批通过"),
    DENY("DENY", "审批未通过"),
    DONE("DONE", "工单关闭");
    private String name;
    private String desc;

    TicketStatus(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
