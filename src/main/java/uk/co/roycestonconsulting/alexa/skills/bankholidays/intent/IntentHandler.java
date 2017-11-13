package uk.co.roycestonconsulting.alexa.skills.bankholidays.intent;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;

/**
 * Intent Handler interface.
 */
public interface IntentHandler {

	/**
	 * Handles the intent
	 *
	 * @param requestEnvelope the {@link SpeechletRequestEnvelope} encapsulating the {@link IntentRequest} and {@link Session}.
	 * @return a {@link SpeechletResponse} that is the result of handling the intent.
	 */
	SpeechletResponse handleIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope);
}
