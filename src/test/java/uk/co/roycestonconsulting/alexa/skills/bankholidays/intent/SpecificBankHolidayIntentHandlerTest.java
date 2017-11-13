package uk.co.roycestonconsulting.alexa.skills.bankholidays.intent;

import static java.time.LocalDate.now;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Context;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.SsmlOutputSpeech;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.model.BankHoliday;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.service.BankHolidayService;

/**
 * Unit test for {@link SpecificBankHolidayIntentHandler}.
 */
@RunWith(MockitoJUnitRunner.class)
public class SpecificBankHolidayIntentHandlerTest {

	private static final String EASTER_MONDAY = "Easter Monday";

	private static final String BANK_HOLIDAY_SLOT_NAME = "bankHoliday";

	private static final String YEAR_SLOT_NAME = "year";

	@Mock
	private BankHolidayService service;

	@InjectMocks
	private SpecificBankHolidayIntentHandler handler;

	@Test
	public void shouldHandleIntentGivenMatchingBankHoliday() {
		// Given
		LocalDate date = now().plusYears(1);
		SpeechletRequestEnvelope<IntentRequest> requestEnvelope =
				buildRequestEnvelope("specificBankHoliday", EASTER_MONDAY, String.valueOf(date.getYear()));
		BankHoliday bankHoliday = aBankHoliday(EASTER_MONDAY, date);
		Optional<BankHoliday> expectedBankHoliday = Optional.of(bankHoliday);
		Optional<Integer> year = Optional.of(date.getYear());
		given(service.findBankHoliday(EASTER_MONDAY, year)).willReturn(expectedBankHoliday);

		// When
		SpeechletResponse speechletResponse = handler.handleIntent(requestEnvelope);

		// Then
		SsmlOutputSpeech outputSpeech = (SsmlOutputSpeech) speechletResponse.getOutputSpeech();
		assertNotNull(outputSpeech);
	}

	private BankHoliday aBankHoliday(String title, LocalDate date) {
		BankHoliday bankHoliday = new BankHoliday();
		bankHoliday.setTitle(title);
		bankHoliday.setDate(date);
		return bankHoliday;
	}

	@Test
	public void shouldHandleIntentGivenBankHolidayNotFound() {
		// Given

		// When

		// Then
	}

	private SpeechletRequestEnvelope<IntentRequest> buildRequestEnvelope(String intentName, String bankHolidayName, String year) {
		return SpeechletRequestEnvelope.<IntentRequest>builder()
				.withVersion("1.0")
				.withRequest(IntentRequest.builder()
						.withRequestId("12345")
						.withIntent(Intent.builder()
								.withName(intentName)
								.withSlot(Slot.builder()
										.withName(BANK_HOLIDAY_SLOT_NAME)
										.withValue(bankHolidayName)
										.build())
								.withSlot(Slot.builder()
										.withName(YEAR_SLOT_NAME)
										.withValue(year)
										.build())
								.build())
						.build())
				.withSession(Session.builder().withSessionId("67890").build())
				.withContext(Context.builder().build())
				.build();
	}
}
