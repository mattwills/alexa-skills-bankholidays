package uk.co.roycestonconsulting.alexa.skills.common;

import static com.amazon.speech.speechlet.SpeechletResponse.newTellResponse;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;

/**
 * Simple factory providing static methods to return {@link Reprompt} instances
 */
public class ResponseFactory {

	/**
	 * @param prompt the prompt text
	 * @return a {@link Reprompt} with the specified text
	 */
	public static Reprompt reprompt(final String prompt) {
		final PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
		outputSpeech.setText(prompt);

		final Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(outputSpeech);

		return reprompt;
	}

	/**
	 * @return a {@link Reprompt} with text asking what the user would like to do next
	 */
	public static Reprompt whatNextReprompt() {
		return reprompt("What would you like me to do next?");
	}

	/**
	 * Outputs the provided text as a {@link SpeechletResponse}.
	 *
	 * @param text The text to output.
	 * @return The {@link SpeechletResponse}.
	 */
	public static SpeechletResponse tellResponse(String text) {
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(text);
		return newTellResponse(speech);
	}

}
