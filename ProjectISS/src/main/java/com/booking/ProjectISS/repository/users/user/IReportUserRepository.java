package com.booking.ProjectISS.repository.users.user;

import com.booking.ProjectISS.model.users.ReportUser;
import com.booking.ProjectISS.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface IReportUserRepository extends JpaRepository<ReportUser,Long> {

    @Query("select r from ReportUser r where r.owner.id = ?1 and r.guest.id=?2 and r.status=?3")
    ReportUser findByOwnerGuestStatus(Long idOwner, Long idGuest, String go);

    @Query("select r from ReportUser r where r.owner.id = ?1 and r.guest.id=?2")
    Collection<ReportUser> findByOwnerGuestStatus1(Long idOwner, Long idGuest);
}
