package org.adorsys.adcost.jpa;

import org.adorsys.javaext.description.Description;

@Description("CstStmntStatus_description")
public enum CstStmntStatus {
	/*
	 * This status is defined whenever a statement is created.
	 */
	@Description("CstStmntStatus_OPEN_description")
	OPEN, 
	/*
	 * The review status just mark that the statement has been checked and 
	 * confirmed by another instances. This can be on the sender or on the
	 * receiver side.
	 */
	@Description("CstStmntStatus_REVIEWED_description")
	REVIEWED, 
	/*
	 * This is generally set by the sender to prevent further modifications.
	 */
	@Description("CstStmntStatus_CLOSED_description")
	CLOSED, 
	/*
	 * The approval can happen from both sides.
	 */
	@Description("CstStmntStatus_APPROVED_description")
	APPROVED, 
	/*
	 * This is the process of releasing it and making it visible to
	 * the receiver of the statement.
	 */
	@Description("CstStmntStatus_SENT_description")
	SENT, 
	/*
	 * Aknowledges reception by the receiving side.
	 */
	@Description("CstStmntStatus_RECEIVED_description")
	RECEIVED, 
	/*
	 * Another review process that can happen on any side.
	 */
	@Description("CstStmntStatus_CHECKED_description")
	CHECKED, 
	/*
	 * Consume by an accounting process.
	 */
	@Description("CstStmntStatus_POSTED_description")
	POSTED
}