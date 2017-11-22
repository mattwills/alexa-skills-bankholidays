package uk.co.roycestonconsulting.alexa.skills.bankholidays.intent;

import static java.time.LocalDate.now;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.intent.BankHolidayIntent.NEXT_SPECIFIC_BANK_HOLIDAY_INTENT;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.intent.BankHolidayName.CHRISTMAS_DAY;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.testsupport.BankHolidayTestDataFactory.aBankHoliday;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.testsupport.SpeechletRequestEnvelopeTestDataFactory.aSpeechletRequestEnvelope;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.testsupport.SpeechletRequestEnvelopeTestDataFactory.aSpeechletRequestEnvelopeWithoutBankHolidayNameSlot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SsmlOutputSpeech;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.model.BankHoliday;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.service.BankHolidayService;

/**
 * Unit test for {@link NextSpecificBankHolidayIntentHandler}.
 */
@RunWith(MockitoJUnitRunner.class)
public class NextSpecificBankHolidayIntentHandlerTest {

	private static final String INTENT_NAME = NEXT_SPECIFIC_BANK_HOLIDAY_INTENT.name();

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-YYYY");

	@Mock
	private BankHolidayService bankHolidayService;

	@InjectMocks
	private NextSpecificBankHolidayIntentHandler handler;

	@Test
	public void shouldHandleIntent() {
		// Given
		String bankHolidayName = CHRISTMAS_DAY.getOfficialName();
		LocalDate bankHolidayDate = now().withDayOfYear(25).withMonth(12);
		String expectedSsml = "<speak>" + bankHolidayName + " is <break time=\"50ms\"/>on: "
				+ "<say-as interpret-as=\"date\" format=\"dmy\">" + bankHolidayDate.format(DATE_FORMATTER)
				+ "</say-as></speak>";

		SpeechletRequestEnvelope<IntentRequest> requestEnvelope =
				aSpeechletRequestEnvelope(INTENT_NAME, bankHolidayName, bankHolidayDate.getYear()).build();
		BankHoliday bankHoliday = aBankHoliday(bankHolidayName, bankHolidayDate).build();
		given(bankHolidayService.getNextOccurenceOfBankHoliday(bankHolidayName)).willReturn(bankHoliday);

		// When
		SpeechletResponse speechletResponse = handler.handleIntent(requestEnvelope);

		// Then
		String actualSsml = ((SsmlOutputSpeech) speechletResponse.getOutputSpeech()).getSsml();
		assertThat(actualSsml, is(expectedSsml));
	}

	@Test
	public void shouldHandleIntentGivenBankHolidaySlotNotProvided() {
		// Given
		String year = String.valueOf(now().getYear());
		String expectedText = "Unable to find a bank holiday of that name, please try again";

		SpeechletRequestEnvelope<IntentRequest> requestEnvelope = aSpeechletRequestEnvelopeWithoutBankHolidayNameSlot(INTENT_NAME, year).build();

		// When
		SpeechletResponse speechletResponse = handler.handleIntent(requestEnvelope);

		// Then
		String actualText = ((PlainTextOutputSpeech) speechletResponse.getOutputSpeech()).getText();
		assertThat(actualText, is(expectedText));
	}

	@Test
	public void shouldFailToHandleIntentGivenBankHolidayNotFound() {
		// Given
		String bankHolidayName = CHRISTMAS_DAY.getOfficialName();
		SpeechletRequestEnvelope<IntentRequest> requestEnvelope = aSpeechletRequestEnvelope().build();
		given(bankHolidayService.getNextOccurenceOfBankHoliday(bankHolidayName)).willThrow(new IllegalStateException("Unable to find bank holiday"));

		// When
		try {
			handler.handleIntent(requestEnvelope);
			fail("Expected IllegalStateException");
		}

		// Then
		catch (IllegalStateException e) {
			assertThat(e.getMessage(), is("Unable to find bank holiday"));
		}
	}
}