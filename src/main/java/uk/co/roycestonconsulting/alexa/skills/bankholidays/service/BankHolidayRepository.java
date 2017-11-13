package uk.co.roycestonconsulting.alexa.skills.bankholidays.service;

import uk.co.roycestonconsulting.alexa.skills.bankholidays.model.BankHolidayEvents;

/**
 * Represents a repository for bank holiday data.
 */
public interface BankHolidayRepository {

	/**
	 * Provides bank holiday data for a particular division (e.g. England and Wales).
	 *
	 * @param division The required {@link Division}.
	 * @return A {@link BankHolidayEvents} containing the relevant bank holiday data.
	 */
	BankHolidayEvents getBankHolidays(Division division);
}
