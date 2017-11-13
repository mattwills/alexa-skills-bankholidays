package uk.co.roycestonconsulting.alexa.skills.bankholidays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Context;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import uk.co.roycestonconsulting.alexa.skills.bankholidays.speechlet.BankHolidaySpeechlet;

/**
 * Unit test for {@link BankHolidaySpeechlet}.
 */
public class BankHolidaySpeechletTest {

	private final BankHolidaySpeechlet speechlet = new BankHolidaySpeechlet();

	@Test
	public void shouldOnLaunch() {
		// Given
		SpeechletRequestEnvelope<LaunchRequest> requestEnvelope = SpeechletRequestEnvelope.<LaunchRequest>builder()
				.withVersion("1.0")
				.withRequest(LaunchRequest.builder().withRequestId("12345").build())
				.withSession(Session.builder().withSessionId("67890").build())
				.withContext(Context.builder().build())
				.build();

		String expectedPlainTextOutputSpeech = "Welcome to the Alexa Bank Holidays skill, you can ask when the next bank holiday is";

		// When
		SpeechletResponse speechletResponse = speechlet.onLaunch(requestEnvelope);

		// Then
		String outputText = ((PlainTextOutputSpeech) speechletResponse.getOutputSpeech()).getText();
		assertThat(outputText, equalTo(expectedPlainTextOutputSpeech));
	}

	@Test
	public void shouldOnIntentGivenValidIntentName() {
		// Given
		SpeechletRequestEnvelope<IntentRequest> requestEnvelope = SpeechletRequestEnvelope.<IntentRequest>builder()
				.withVersion("1.0")
				.withRequest(IntentRequest.builder()
						.withRequestId("12345")
						.withIntent(Intent.builder().withName("nextBankHoliday").build())
						.build())
				.withSession(Session.builder().withSessionId("67890").build())
				.withContext(Context.builder().build())
				.build();

		// When
		SpeechletResponse speechletResponse = speechlet.onIntent(requestEnvelope);

		// Then
		OutputSpeech outputSpeech = speechletResponse.getOutputSpeech();
		assertNotNull(outputSpeech);
	}

	@Test
	public void shouldFailToOnIntentGivenUnknownIntentName() {
		// Given
		SpeechletRequestEnvelope<IntentRequest> requestEnvelope = SpeechletRequestEnvelope.<IntentRequest>builder()
				.withVersion("1.0")
				.withRequest(IntentRequest.builder()
						.withRequestId("12345")
						.withIntent(Intent.builder().withName("Unknown-Intent").build())
						.build())
				.withSession(Session.builder().withSessionId("67890").build())
				.withContext(Context.builder().build())
				.build();

		// When
		try {
			speechlet.onIntent(requestEnvelope);
			fail("Was expecting an IllegalArgumentException");
		}
		// Then
		catch (IllegalArgumentException e) {
			Assertions.assertThat(e.getMessage()).isEqualTo("No intent with name Unknown-Intent");
		}
	}
}
