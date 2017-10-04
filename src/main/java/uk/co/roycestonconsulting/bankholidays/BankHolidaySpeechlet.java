package uk.co.roycestonconsulting.bankholidays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

/**
 * A simple implementation of {@link com.amazon.speech.speechlet.SpeechletV2} for handling bank holiday related requests.
 */
public class BankHolidaySpeechlet extends AbstractSpeechlet {

	private static final String NEXT_BANK_HOLIDAY = "nextBankHoliday";

	private static final String AMAZON_HELP_INTENT = "AMAZON.HelpIntent";

	private static final Logger LOG = LoggerFactory.getLogger(BankHolidaySpeechlet.class);

	@Override
	public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
		return getWelcomeResponse();
	}

	@Override
	public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		LOG.debug("onIntent requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(), requestEnvelope.getSession().getSessionId());

		Intent intent = requestEnvelope.getRequest().getIntent();
		String intentName = (intent != null) ? intent.getName() : null;

		if (NEXT_BANK_HOLIDAY.equals(intentName)) {
			return getNextBankHolidayResponse();
		} else if (AMAZON_HELP_INTENT.equals(intentName)) {
			return getHelpResponse();
		} else {
			throw new IllegalArgumentException("Invalid Intent " + intentName);
		}
	}

	/**
	 * Creates and returns a {@code SpeechletResponse} with a welcome message.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getWelcomeResponse() {
		String speechText = "Welcome to the Alexa Bank Holidays skill, you can ask when the next bank holiday is";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("Welcome");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		// Create reprompt
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);

		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}

	/**
	 * Creates a {@code SpeechletResponse} for the next bank holiday intent.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getNextBankHolidayResponse() {
		String speechText = "The next UK Bank Holiday is on 28th May 2018";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("Next Bank Holiday");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		return SpeechletResponse.newTellResponse(speech, card);
	}

	/**
	 * Creates a {@code SpeechletResponse} for the help intent.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getHelpResponse() {
		String speechText = "You can ask when the next bank holiday is";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("Next Bank Holiday help");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		// Create reprompt
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);

		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}
}
