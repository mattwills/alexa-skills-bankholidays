package uk.co.roycestonconsulting.alexa.skills.bankholidays.testsupport;

import static uk.co.roycestonconsulting.alexa.skills.bankholidays.testsupport.BankHolidayTestDataFactory.aBankHoliday;

import java.util.Collections;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.model.BankHolidayEvents;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.model.BankHolidayEvents.BankHolidayEventsBuilder;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.service.Division;

/**
 * Test data factory for {@link BankHolidayEvents}.
 */
public class BankHolidayEventsTestDataFactory {

	/**
	 * Provides a {@link BankHolidayEventsBuilder} capable of building a valid {@link BankHolidayEvents} instance.
	 *
	 * @return a {@link BankHolidayEventsBuilder}.
	 */
	public static BankHolidayEventsBuilder aBankHolidayEvents() {
		return BankHolidayEvents.builder()
				.division(Division.ENGLAND_AND_WALES.getValue())
				.events(Collections.singletonList(aBankHoliday().build()));
	}
}
