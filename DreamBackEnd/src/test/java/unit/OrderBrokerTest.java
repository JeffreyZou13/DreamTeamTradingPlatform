package unit;

import com.citi.dream.jms.MessageSender;
import com.citi.dream.jms.MockBroker;
import com.citi.dream.repos.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ConfigurableApplicationContext;

public class OrderBrokerTest {

    @InjectMocks
    MockBroker broker;

    @Mock
    private MessageSender sender;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ConfigurableApplicationContext context;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sendMessageIsSentToBroker() {

    }
}