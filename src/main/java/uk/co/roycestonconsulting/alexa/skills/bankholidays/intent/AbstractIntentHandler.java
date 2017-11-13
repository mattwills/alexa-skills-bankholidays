package uk.co.roycestonconsulting.alexa.skills.bankholidays.intent;

import static java.time.LocalDate.now;
import static uk.co.roycestonconsulting.alexa.skills.common.SsmlOutputSpeechBuilder.aSsmlOutputSpeechBuilder;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.slf4j.Logger;
import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.model.BankHoliday;

/**
 * Abstract class for all {@link IntentHandler} implementations.
 */
public abstract class AbstractIntentHandler implements IntentHandler {

	protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-YYYY");

	protected static final String BANK_HOLIDAY_SLOT_NAME = "bankHoliday";

	private static final String YEAR_SLOT_NAME = "year";

	private static final String EASTER_SUNDAY = "Easter Sunday";

	private static final String EASTER = "Easter";

	private static final String EASTER_MONDAY = "Easter Monday";

	protected abstract Logger getLogger();

	protected String getBankHolidayName(String bankHolidayName) {
		getLogger().debug("Requested bank holiday name: {}", bankHolidayName);

		// Easter isn't a bank holiday, so look up Easter Monday and then subtract a day later
		if (easterSundayRequested(bankHolidayName)) {
			bankHolidayName = EASTER_MONDAY;
		}
		getLogger().debug("Searching for: {}", bankHolidayName);
		return bankHolidayName;
	}

	protected Optional<Integer> getYear(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		Optional<Slot> yearSlot = getSlot(requestEnvelope, YEAR_SLOT_NAME);
		return yearSlot.map(slot -> Integer.valueOf(slot.getValue()));
	}

	protected String buildOutput(String requestedBankHolidayName, BankHoliday bankHoliday) {
		// If the user asked for Easter Sunday, then subtract a day
		if (easterSundayRequested(requestedBankHolidayName)) {
			bankHoliday.setDate(bankHoliday.getDate().minusDays(1));
			bankHoliday.setTitle(EASTER_SUNDAY);
			getLogger().debug("Reverted bank holiday name to: {}", bankHoliday.getTitle());
		}

		String tense = bankHoliday.getDate().isBefore(now()) ? " was " : " is ";

		return aSsmlOutputSpeechBuilder()
				.with(bankHoliday.getTitle())
				.with(tense)
				.withBreak(50)
				.with("on: ")
				.withSayAs("date", "dmy", bankHoliday.getDate().format(DATE_FORMATTER))
				.build();
	}

	private boolean easterSundayRequested(String bankHolidayName) {
		return bankHolidayName.equalsIgnoreCase(EASTER_SUNDAY) || bankHolidayName.equalsIgnoreCase(EASTER);
	}

	/**
	 * Convenience method for extracting a desired {@link Slot} from the {@link SpeechletRequestEnvelope}.
	 *
	 * @param requestEnvelope The {@link SpeechletRequestEnvelope}.
	 * @param slotName The name of the {@link Slot}.
	 * @return An {@link Optional} containing the {@link Slot} if found.
	 */
	protected Optional<Slot> getSlot(SpeechletRequestEnvelope<IntentRequest> requestEnvelope, String slotName) {
		return Optional.ofNullable(requestEnvelope.getRequest().getIntent().getSlot(slotName));
	}
}
