package org.adorsys.adaptmt.shedules;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adaptmt.jpa.AptAptmt;
import org.adorsys.adaptmt.jpa.AptmtStatus;
import org.adorsys.adaptmt.repo.AptAptmtRepository;
import org.apache.commons.lang3.time.DateFormatUtils;

@Stateless
public class AptAptmtEventGateway {

	@Inject
	AptAptmtRepository aptAptmtRepository;

	Logger loggger = Logger.getLogger(AptAptmtEventGateway.class
			.getSimpleName());

	public void handleAptAptmtOngoingEvent(
			@Observes @AptAptmtOngoingEvent AptAptmt aptAptmt) {
//		loggger.log(Level.INFO,
//				" Event : ----------------> check saved appointements status ");
		checkCurrentAptStatus(aptAptmt);
	}

//	@Schedule(minute = "*/1", hour = "*")
//	public void checkAppointementDateForStatus() {
//
//		loggger.log(Level.INFO, " check appointements date every minute ");
//		List<AptAptmt> aptaptmts = aptAptmtRepository.findAll();
//
//		for (AptAptmt aptAptmt : aptaptmts) {
//			checkCurrentAptStatus(aptAptmt);
//		}
//	}
	
	public void checkCurrentAptStatus(AptAptmt aptAptmt){
		
		Date now = new Date();
		String nowStrDate = DateFormatUtils.ISO_DATE_FORMAT.format(now);
		String aptStrDate = DateFormatUtils.ISO_DATE_FORMAT.format(aptAptmt
				.getAppointmentDate());

		String nowStrHour = DateFormatUtils.format(now, "HH");
		String aptStrHour = DateFormatUtils.format(
				aptAptmt.getAppointmentDate(), "HH");

		if ((nowStrDate.equals(aptStrDate))
				&& (nowStrHour.equals(aptStrHour))) {

			if (aptAptmt.getStatus().equals(AptmtStatus.FORTHCOMMING)) {
//				loggger.log(Level.INFO,
//						" now we can change status of appointment to ONGOING");
				
				aptAptmt.setStatus(AptmtStatus.ONGOING);
				aptAptmtRepository.save(aptAptmt);
			}
		}
	}
}
