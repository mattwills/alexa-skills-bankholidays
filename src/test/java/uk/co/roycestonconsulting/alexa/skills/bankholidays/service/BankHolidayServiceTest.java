package uk.co.roycestonconsulting.alexa.skills.bankholidays.service;

import static java.time.LocalDate.now;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.intent.BankHolidayName.BOXING_DAY;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.intent.BankHolidayName.CHRISTMAS_DAY;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.service.Division.ENGLAND_AND_WALES;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.testsupport.BankHolidayEventsTestDataFactory.aBankHolidayEvents;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.testsupport.BankHolidayTestDataFactory.aBankHoliday;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import com.google.common.collect.ImmutableList;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.intent.BankHolidayName;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.model.BankHoliday;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.model.BankHolidayEvents;

/**
 * Unit test for {@link BankHolidayService}.
 */
@RunWith(MockitoJUnitRunner.class)
public class BankHolidayServiceTest {

	@Mock
	private BankHolidayRepository bankHolidayRepository;

	@InjectMocks
	private BankHolidayService bankHolidayService;

	@Test
	public void shouldGetNextBankHoliday() {
		// Given
		List<BankHoliday> events = bankHolidayEvents();
		BankHolidayEvents bankHolidays = aBankHolidayEvents().events(events).build();
		BankHoliday expectedBankHoliday = bankHolidays.getEvents().get(0);
		given(bankHolidayRepository.getBankHolidays(ENGLAND_AND_WALES)).willReturn(bankHolidays);

		// When
		BankHoliday bankHoliday = bankHolidayService.getNextBankHoliday();

		// Then
		assertThat(bankHoliday, is(expectedBankHoliday));
	}

	@Test
	public void shouldFailToGetNextBankHolidayGivenNoFutureEvents() {
		// Given
		BankHolidayEvents bankHolidays = aBankHolidayEvents().events(emptyList()).build();
		given(bankHolidayRepository.getBankHolidays(ENGLAND_AND_WALES)).willReturn(bankHolidays);

		try {
			// When
			bankHolidayService.getNextBankHoliday();
			fail("Expected IllegalStateException");
		}

		// Then
		catch (IllegalStateException e) {
			assertThat(e.getMessage(), is("Unable to find next bank holiday"));
		}
	}

	@Test
	public void shouldGetNextOccurrenceOfBankHoliday() {
		// Given
		List<BankHoliday> events = bankHolidayEvents();
		BankHolidayEvents bankHolidays = aBankHolidayEvents().events(events).build();
		BankHoliday expectedBankHoliday = bankHolidays.getEvents().get(0);
		given(bankHolidayRepository.getBankHolidays(ENGLAND_AND_WALES)).willReturn(bankHolidays);

		// When
		BankHoliday bankHoliday = bankHolidayService.getNextOccurenceOfBankHoliday(expectedBankHoliday.getTitle());

		// Then
		assertThat(bankHoliday, is(expectedBankHoliday));
	}

	@Test
	public void shouldFailToGetNextOccurrenceOfBankHolidayGivenNoFutureEvents() {
		// Given
		BankHolidayEvents bankHolidays = aBankHolidayEvents().events(emptyList()).build();
		given(bankHolidayRepository.getBankHolidays(ENGLAND_AND_WALES)).willReturn(bankHolidays);

		try {
			// When
			bankHolidayService.getNextOccurenceOfBankHoliday(CHRISTMAS_DAY.getOfficialName());
			fail("Expected IllegalStateException");
		}

		// Then
		catch (IllegalStateException e) {
			assertThat(e.getMessage(), is("Unable to find next occurrence of bank holiday " + CHRISTMAS_DAY.getOfficialName()));
		}
	}

	@Test
	public void shouldFindBankHoliday() {
		// Given
		List<BankHoliday> events = bankHolidayEvents();
		BankHolidayEvents bankHolidays = aBankHolidayEvents().events(events).build();
		Optional<Integer> year = Optional.of(now().getYear());
		BankHoliday expectedBankHoliday = bankHolidays.getEvents().get(0);
		given(bankHolidayRepository.getBankHolidays(ENGLAND_AND_WALES)).willReturn(bankHolidays);

		// When
		Optional<BankHoliday> bankHoliday = bankHolidayService.findBankHoliday(expectedBankHoliday.getTitle(), year);

		// Then
		assertThat(bankHoliday.isPresent(), is(true));
		assertThat(bankHoliday.get(), is(expectedBankHoliday));
	}

	@Test
	public void shouldNotFindBankHolidayGivenPreviousYear() {
		// Given
		List<BankHoliday> events = bankHolidayEvents();
		BankHolidayEvents bankHolidays = aBankHolidayEvents().events(events).build();
		Optional<Integer> year = Optional.of(now().minusYears(1).getYear());
		given(bankHolidayRepository.getBankHolidays(ENGLAND_AND_WALES)).willReturn(bankHolidays);

		// When
		Optional<BankHoliday> bankHoliday = bankHolidayService.findBankHoliday(CHRISTMAS_DAY.getOfficialName(), year);

		// Then
		assertThat(bankHoliday.isPresent(), is(false));
	}

	@Test
	public void shouldNotFindBankHolidayGivenEventDoesNotExist() {
		// Given
		List<BankHoliday> events = bankHolidayEvents();
		BankHolidayEvents bankHolidays = aBankHolidayEvents().events(events).build();
		Optional<Integer> year = Optional.of(now().getYear());
		given(bankHolidayRepository.getBankHolidays(ENGLAND_AND_WALES)).willReturn(bankHolidays);

		// When
		Optional<BankHoliday> bankHoliday = bankHolidayService.findBankHoliday("UNKNOWN", year);

		// Then
		assertThat(bankHoliday.isPresent(), is(false));
	}

	private List<BankHoliday> bankHolidayEvents() {
		return ImmutableList.of(
				bankHoliday(CHRISTMAS_DAY, now().plusDays(1)),
				bankHoliday(BOXING_DAY, now().plusDays(2)),
				bankHoliday(CHRISTMAS_DAY, now().plusYears(1)));
	}

	private BankHoliday bankHoliday(BankHolidayName name, LocalDate date) {
		return aBankHoliday()
				.title(name.getOfficialName())
				.date(date).build();
	}
}