package org.apache.camel.component.http;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultHttpBindingTest {

    private static final String BODY_SIZE_60 = "012345678901234567890123456789012345678901234657890123456789";
    private static final String BODY_SIZE_80 = "01234567890123456789012345678901234567890123465789012345678901234567890123456789";

    @Mock
    private HttpServletResponse response;

    private Message message;

    private Exchange exchange;

    private ServletOutputStreamTestImpl testOutputStream;

    DefaultHttpBinding binding;

    @Before
    public void init() throws IOException {
        message = new DefaultMessage();
        exchange = new DefaultExchange(new DefaultCamelContext());
        HttpEndpoint httpEndpoint = new HttpEndpoint();
        httpEndpoint.setChunked(false);
        exchange.setFromEndpoint(httpEndpoint);
        testOutputStream = new ServletOutputStreamTestImpl();
        when(response.getOutputStream()).thenReturn(testOutputStream);
        binding = new DefaultHttpBinding();
    }

    @Test
    public void testDoWriteDirectResponseSuccess() throws IOException {
        message.setBody(BODY_SIZE_60.getBytes("UTF-8"));
        binding.doWriteDirectResponse(message, response, exchange);
        assertEquals(BODY_SIZE_60, testOutputStream.toString());
    }

    // Upload file max size is configured by HttpUtilities.MaxUploadFileBytes in .esapi/ESAPI.properties
    @Test(expected = IOException.class)
    public void testDoWriteDirectResponseInputExceedsMaxFileSize() throws IOException {
        message.setBody(BODY_SIZE_80.getBytes("UTF-8"));
        binding.doWriteDirectResponse(message, response, exchange);
        assertEquals(BODY_SIZE_80, testOutputStream.toString());
    }

}