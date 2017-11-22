package uk.co.roycestonconsulting.alexa.skills.bankholidays.intent;

import static com.amazon.speech.speechlet.SpeechletResponse.newAskResponse;
import static uk.co.roycestonconsulting.alexa.skills.common.ResponseFactory.whatNextReprompt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;

/**
 * {@link IntentHandler} for help prompt.
 */
public class HelpIntentHandler implements IntentHandler {

	private static final Logger LOG = LoggerFactory.getLogger(HelpIntentHandler.class);

	@Override
	public SpeechletResponse handleIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		LOG.debug("handleIntent requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(), requestEnvelope.getSession().getSessionId());

		String speechText = "You can ask when the next bank holiday is";

		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		return newAskResponse(speech, whatNextReprompt());
	}
}
