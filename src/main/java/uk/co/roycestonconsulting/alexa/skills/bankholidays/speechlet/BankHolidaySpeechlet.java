package uk.co.roycestonconsulting.alexa.skills.bankholidays.speechlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.intent.BankHolidayIntent;

/**
 * A simple implementation of {@link com.amazon.speech.speechlet.SpeechletV2} for handling bank holiday related requests.
 */
public class BankHolidaySpeechlet extends AbstractSpeechlet {

	private static final Logger LOG = LoggerFactory.getLogger(BankHolidaySpeechlet.class);

	@Override
	public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
		String speechText = "Welcome to the Alexa Bank Holidays skill, you can ask when the next bank holiday is";

		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);

		return SpeechletResponse.newAskResponse(speech, reprompt);
	}

	@Override
	public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		LOG.debug("onIntent requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
				requestEnvelope.getSession().getSessionId());

		String intentName = getIntentName(requestEnvelope);
		return BankHolidayIntent.from(intentName)
				.orElseThrow(() -> new IllegalArgumentException("No intent with name " + intentName))
				.getIntentHandler()
				.handleIntent(requestEnvelope);
	}

	private String getIntentName(final SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		return requestEnvelope.getRequest().getIntent().getName();
	}

}
