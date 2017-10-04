package uk.co.roycestonconsulting.bankholidays;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Unit test for {@link BankHolidaySpeechletRequestStreamHandler}.
 */
public class BankHolidaySpeechletRequestStreamHandlerTest {

	@Test
	public void shouldConstruct() {
		// Given
		BankHolidaySpeechletRequestStreamHandler handler = null;

		// When
		handler = new BankHolidaySpeechletRequestStreamHandler();

		// Then
		assertThat(handler, notNullValue());
	}

}
