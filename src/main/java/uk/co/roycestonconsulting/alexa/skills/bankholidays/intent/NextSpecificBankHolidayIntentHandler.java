package uk.co.roycestonconsulting.alexa.skills.bankholidays.intent;

import static com.amazon.speech.speechlet.SpeechletResponse.newTellResponse;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.SsmlOutputSpeech;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.model.BankHoliday;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.service.BankHolidayService;

/**
 * {@link IntentHandler} for a looking up the next occurrence of a specific bank holiday. This includes Easter Sunday,
 * even though it's not a bank holiday, but there's a high chance people will ask something like "when's Easter next?".
 */
public class NextSpecificBankHolidayIntentHandler extends AbstractBankHolidayIntentHandler {

	private static final Logger LOG = LoggerFactory.getLogger(NextSpecificBankHolidayIntentHandler.class);

	private BankHolidayService bankHolidayService = new BankHolidayService();

	@Override
	public SpeechletResponse handleIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		LOG.debug("handleIntent requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(), requestEnvelope.getSession().getSessionId());

		Optional<Slot> bankHolidaySlot = getSlot(requestEnvelope, BANK_HOLIDAY_SLOT_NAME);
		if (!bankHolidaySlot.isPresent()) {
			LOG.debug("Cannot find bank holiday slot");
			return unknownBankHolidayResponse();
		}
		Optional<BankHolidayName> requestedBankHolidayName = BankHolidayName.fromName(bankHolidaySlot.get().getValue());
		if (!requestedBankHolidayName.isPresent()) {
			LOG.debug("Unrecognised bank holiday");
			return unknownBankHolidayResponse();
		}

		String officialName = getOfficialName(requestedBankHolidayName.get());
		BankHoliday bankHoliday = bankHolidayService.getNextOccurenceOfBankHoliday(officialName);

		String ssmlOutput = buildSpecificBankHolidayOutput(requestedBankHolidayName.get(), bankHoliday);
		LOG.debug("ssmlOutout={}", ssmlOutput);

		SsmlOutputSpeech speech = new SsmlOutputSpeech();
		speech.setSsml(ssmlOutput);
		return newTellResponse(speech);
	}

	@Override
	protected Logger getLogger() {
		return LOG;
	}
}
