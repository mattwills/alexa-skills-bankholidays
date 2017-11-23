package uk.co.roycestonconsulting.alexa.skills.bankholidays.intent;

import static com.amazon.speech.speechlet.SpeechletResponse.newAskResponse;
import static java.time.LocalDate.now;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.intent.BankHolidayName.EASTER;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.intent.BankHolidayName.EASTER_MONDAY;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.intent.BankHolidayName.EASTER_SUNDAY;
import static uk.co.roycestonconsulting.alexa.skills.common.ResponseFactory.whatNextReprompt;
import static uk.co.roycestonconsulting.alexa.skills.common.SsmlOutputSpeechBuilder.aSsmlOutputSpeechBuilder;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.model.BankHoliday;

/**
 * Abstract class for all {@link IntentHandler} implementations.
 */
public abstract class AbstractBankHolidayIntentHandler implements IntentHandler {

	protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-YYYY");

	protected static final String BANK_HOLIDAY_SLOT_NAME = "bankHoliday";

	private static final String YEAR_SLOT_NAME = "year";

	protected abstract Logger getLogger();

	/**
	 * Provides the official bank holiday name to search for, based on whether the given name is the official name.
	 *
	 * <p>
	 * N.B. It also substitutes "Easter" or "Easter Sunday" for "Easter Monday", since the former isn't a bank holiday and won't be found in the repository.
	 *
	 * @param name The requested name of the bank holiday.
	 * @return The official name of the bank holiday, which may be different from the requested name.
	 */
	protected String getOfficialName(BankHolidayName name) {
		getLogger().debug("Requested bank holiday name: {}", name.getName());

		// Easter isn't a bank holiday, so look up Easter Monday and then subtract a day later
		if (easterSundayRequested(name)) {
			name = EASTER_MONDAY;
		}
		getLogger().debug("Searching for: {}", name.getOfficialName());
		return name.getOfficialName();
	}

	/**
	 * Provides the year to use when searching for a bank holiday, but only if the requestEnvelope contains a year slot.
	 *
	 * @param requestEnvelope The {@link SpeechletRequestEnvelope<IntentRequest>} potentially containing the year slot.
	 * @return The {@link Optional} year value.
	 */
	protected Optional<Integer> getYear(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		Optional<Slot> yearSlot = getSlot(requestEnvelope, YEAR_SLOT_NAME);
		if (yearSlot.isPresent()) {
			String value = yearSlot.map(Slot::getValue).get();
			return StringUtils.isNumeric(value) ? Optional.of(Integer.valueOf(value)) : Optional.empty();
		}
		return Optional.empty();
	}

	/**
	 * Builds a {@link String} containing the required ssml output for a requested bank holiday.
	 *
	 * @param requestedBankHolidayName The name of the requested bank holiday from the input slot, which could differ from the matched
	 * bank holiday (e.g. Easter Sunday vs Easter Monday).
	 * @param bankHoliday The {@link BankHoliday} containing details of the matched bank holiday.
	 * @return The {@link String} containing the ssml.
	 */
	protected String buildSpecificBankHolidayOutput(BankHolidayName requestedBankHolidayName, BankHoliday bankHoliday) {
		// If the user asked for Easter Sunday, then subtract a day
		if (easterSundayRequested(requestedBankHolidayName)) {
			bankHoliday.revertToEasterSunday();
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

	protected SpeechletResponse unknownBankHolidayResponse() {
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText("Unable to find a bank holiday of that name, please try again");
		return newAskResponse(speech, whatNextReprompt());
	}

	private boolean easterSundayRequested(BankHolidayName bankHolidayName) {
		return bankHolidayName.equals(EASTER_SUNDAY) || bankHolidayName.equals(EASTER);
	}
}
