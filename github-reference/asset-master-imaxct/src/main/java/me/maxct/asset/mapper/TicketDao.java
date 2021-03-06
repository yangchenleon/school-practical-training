package me.maxct.asset.mapper;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import me.maxct.asset.domain.Ticket;
import me.maxct.asset.dto.TicketVO;
import me.maxct.asset.enumerate.TicketStatus;

/**
 * @author imaxct
 * 2019-03-21 13:04
 */
@Repository
public interface TicketDao extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByPropertyIdAndCurStatus(Long propertyId, TicketStatus curStatus);

    /**
     * 获取某人的工单记录
     * @param applyUserId 提交者id
     * @return return
     */
    @Query("select new me.maxct.asset.dto.TicketVO(t, p.propertyId, p.name, u.name, s) "
           + "from Ticket t left join Property p on t.propertyId = p.id "
           + "left join Step s on t.curStepId = s.id left join User u on t.applyUserId = u.id "
           + "left join ProcessLog pl on t.id = pl.ticketId "
           + "where t.applyUserId = :id or pl.processUserId = :id")
    List<TicketVO> getTicketSimpleList(@Param("id") Long applyUserId);

    /**
     * 获取待办工单记录
     * @return return
     */
    @Query("select new me.maxct.asset.dto.TicketVO(t, p.propertyId, p.name, u.name, s) "
           + "from Ticket t left join Property p on t.propertyId = p.id "
           + "left join Step s on t.curStepId = s.id left join User u on t.applyUserId = u.id "
           + "where t.depId = :id and t.curStatus = me.maxct.asset.enumerate.TicketStatus.PROCESSING")
    List<TicketVO> getDepTicketList(@Param("id") Long depId);

    @Query("select new me.maxct.asset.dto.TicketVO(t, p.propertyId, p.name, u.name, s, pro.name) "
           + "from Ticket t left join Property p on t.propertyId = p.id "
           + "left join Step s on t.curStepId = s.id left join User u on t.applyUserId = u.id "
           + "left join Process pro on t.processId = pro.id where t.processId = :id")
    Page<Ticket> listByProcessType(@Param("id") Long processId, Pageable pageable);

    @Query("select new me.maxct.asset.dto.TicketVO(t, p.propertyId, p.name, u.name, s, pro.name) "
           + "from Ticket t left join Property p on t.propertyId = p.id "
           + "left join Step s on t.curStepId = s.id left join User u on t.applyUserId = u.id "
           + "left join Process pro on t.processId = pro.id where t.id = :id")
    TicketVO getDetail(@Param("id") Long ticketId);
}
