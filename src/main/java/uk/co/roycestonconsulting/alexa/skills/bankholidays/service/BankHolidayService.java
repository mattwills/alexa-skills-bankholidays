package uk.co.roycestonconsulting.alexa.skills.bankholidays.service;

import static java.lang.String.format;
import static java.time.LocalDate.now;
import static java.util.Comparator.comparing;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.service.Division.ENGLAND_AND_WALES;

import java.time.LocalDate;
import java.util.Optional;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.model.BankHoliday;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.model.BankHolidayEvents;

/**
 * Service for retriving the dates of requested bank holidays.
 */
public class BankHolidayService {

	private BankHolidayRepository repository = new JsonFileBankHolidayRepository();

	/**
	 * Retrieves the next {@link BankHoliday}, regardless of what the bank holiday is.
	 *
	 * @return The next {@link BankHoliday}.
	 */
	public BankHoliday getNextBankHoliday() {
		BankHolidayEvents bankHolidays = repository.getBankHolidays(ENGLAND_AND_WALES);
		LocalDate today = now();
		BankHoliday nextBankHoliday = bankHolidays.getEvents().stream()
				.filter(event -> event.getDate().isAfter(today))
				.min(comparing(BankHoliday::getDate))
				.orElseThrow(() -> new IllegalStateException("Unable to find next bank holiday"));

		return nextBankHoliday;
	}

	/**
	 * Gets the next occurrence of a particular {@link BankHoliday}.
	 *
	 * @param bankHolidayName The name of the bank holiday.
	 * @return The next {@link BankHoliday}.
	 */
	public BankHoliday getNextOccurenceOfBankHoliday(String bankHolidayName) {
		BankHolidayEvents bankHolidays = repository.getBankHolidays(ENGLAND_AND_WALES);
		LocalDate today = now();
		BankHoliday bankHoliday = bankHolidays.getEvents().stream()
				.filter(event -> event.getTitle().equalsIgnoreCase(bankHolidayName))
				.filter(event -> event.getDate().isAfter(today))
				.min(comparing(BankHoliday::getDate))
				.orElseThrow(() -> new IllegalStateException(format("Unable to find next occurrence of bank holiday %s", bankHolidayName)));

		return bankHoliday;
	}

	/**
	 * Searches for a {@link BankHoliday} by its name and year.
	 *
	 * @param bankHolidayName The name of the bank holiday.
	 * @param optionalYear    The year of the bank holiday.
	 * @return An {@link Optional} matching {@link BankHoliday}.
	 */
	public Optional<BankHoliday> findBankHoliday(String bankHolidayName, Optional<Integer> optionalYear) {
		BankHolidayEvents bankHolidays = repository.getBankHolidays(ENGLAND_AND_WALES);

		int year = getYear(optionalYear);
		return bankHolidays.getEvents().stream()
				.filter(event -> event.getDate().getYear() == year)
				.filter(event -> event.getTitle().equalsIgnoreCase(bankHolidayName))
				.findFirst();
	}

	private int getYear(Optional<Integer> optionalYear) {
		int year = now().getYear();
		if (optionalYear.isPresent()) {
			year = optionalYear.get();
		}
		return year;
	}
}
