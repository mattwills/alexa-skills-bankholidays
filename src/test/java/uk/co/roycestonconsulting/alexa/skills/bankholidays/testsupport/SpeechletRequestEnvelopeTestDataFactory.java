package uk.co.roycestonconsulting.alexa.skills.bankholidays.testsupport;

import static java.time.LocalDate.now;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.intent.BankHolidayIntent.NEXT_SPECIFIC_BANK_HOLIDAY_INTENT;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.intent.BankHolidayName.CHRISTMAS_DAY;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Context;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;

/**
 * Test data factory for instantiating a {@link SpeechletRequestEnvelope} in a valid state.
 */
public class SpeechletRequestEnvelopeTestDataFactory {

	private static final String BANK_HOLIDAY_SLOT_NAME = "bankHoliday";

	private static final String YEAR_SLOT_NAME = "year";

	public static SpeechletRequestEnvelope.Builder<IntentRequest> aSpeechletRequestEnvelope() {
		return aSpeechletRequestEnvelope(NEXT_SPECIFIC_BANK_HOLIDAY_INTENT.name(), CHRISTMAS_DAY.getOfficialName(), now().getYear());
	}

	public static SpeechletRequestEnvelope.Builder<IntentRequest> aSpeechletRequestEnvelope(String intentName, String bankHolidayName, int year) {
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
										.withValue(String.valueOf(year))
										.build())
								.build())
						.build())
				.withSession(Session.builder().withSessionId("67890").build())
				.withContext(Context.builder().build());
	}

	public static SpeechletRequestEnvelope.Builder<IntentRequest> aSpeechletRequestEnvelopeWithoutBankHolidayNameSlot(String intentName, String year) {
		return SpeechletRequestEnvelope.<IntentRequest>builder()
				.withVersion("1.0")
				.withRequest(IntentRequest.builder()
						.withRequestId("12345")
						.withIntent(Intent.builder()
								.withName(intentName)
								.withSlot(Slot.builder()
										.withName(YEAR_SLOT_NAME)
										.withValue(year)
										.build())
								.build())
						.build())
				.withSession(Session.builder().withSessionId("67890").build())
				.withContext(Context.builder().build());
	}

	public static SpeechletRequestEnvelope.Builder<IntentRequest> aSpeechletRequestEnvelopeWithoutYearSlot(String intentName, String bankHolidayName) {
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
								.build())
						.build())
				.withSession(Session.builder().withSessionId("67890").build())
				.withContext(Context.builder().build());
	}
}
