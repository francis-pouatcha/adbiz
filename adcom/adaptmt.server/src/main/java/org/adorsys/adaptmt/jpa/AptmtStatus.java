package org.adorsys.adaptmt.jpa;

import org.adorsys.javaext.description.Description;

public enum AptmtStatus {
	@Description("AptmtStatus_FORTHCOMMING_description")
	FORTHCOMMING, @Description("AptmtStatus_ONGOING_description")
	ONGOING, @Description("AptmtStatus_CANCEL_description")
	CANCEL, @Description("AptmtStatus_CLOSED_description")
	CLOSED, @Description("AptmtStatus_REPPORTED_description")
	REPPORTED
}
