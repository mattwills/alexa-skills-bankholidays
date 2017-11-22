package uk.co.roycestonconsulting.alexa.skills.bankholidays.testsupport;

import static java.time.LocalDate.now;

import java.time.LocalDate;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.model.BankHoliday;

public class BankHolidayTestDataFactory {

	public static BankHoliday.BankHolidayBuilder aBankHoliday() {
		return aBankHoliday("Christmas Day", LocalDate.of(now().getYear(), 12, 25));
	}

	public static BankHoliday.BankHolidayBuilder aBankHoliday(String title, LocalDate date) {
		return BankHoliday.builder()
				.title(title)
				.date(date);
	}

}
