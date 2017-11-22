package uk.co.roycestonconsulting.alexa.skills.bankholidays.intent;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static uk.co.roycestonconsulting.alexa.skills.bankholidays.testsupport.SpeechletRequestEnvelopeTestDataFactory.aSpeechletRequestEnvelope;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;

/**
 * Unit test for {@link HelpIntentHandler}.
 */
@RunWith(MockitoJUnitRunner.class)
public class HelpIntentHandlerTest {

	@InjectMocks
	private HelpIntentHandler handler;

	@Test
	public void shouldHandleIntent() {
		// Given
		String expectedText = "You can ask when the next bank holiday is";
		SpeechletRequestEnvelope<IntentRequest> requestEnvelope = aSpeechletRequestEnvelope().build();

		// When
		SpeechletResponse speechletResponse = handler.handleIntent(requestEnvelope);

		// Then
		String actualText = ((PlainTextOutputSpeech) speechletResponse.getOutputSpeech()).getText();
		assertThat(actualText, is(expectedText));
	}

}