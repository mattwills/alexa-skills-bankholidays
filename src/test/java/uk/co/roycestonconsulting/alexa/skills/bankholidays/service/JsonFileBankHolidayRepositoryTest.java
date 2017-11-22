package uk.co.roycestonconsulting.alexa.skills.bankholidays.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.service.Division.ENGLAND_AND_WALES;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.model.BankHolidayEvents;

/**
 * Unit test for {@link JsonFileBankHolidayRepository}.
 */
@RunWith(MockitoJUnitRunner.class)
public class JsonFileBankHolidayRepositoryTest {

	@InjectMocks
	private JsonFileBankHolidayRepository repository;

	@Test
	public void shouldGetBankHolidays() {
		// Given
		Division division = ENGLAND_AND_WALES;

		// When
		BankHolidayEvents bankHolidays = repository.getBankHolidays(division);

		// Then
		assertNotNull(bankHolidays);
		assertNotNull(bankHolidays.getEvents());
		assertFalse(bankHolidays.getEvents().isEmpty());
	}

}