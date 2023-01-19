package ws.webchat.app;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class AppApplicationTests {

    @Autowired
    private AppApplication appApplication;

    @Test
    public void contextLoad() throws Exception {
        assertThat(appApplication).isNotNull();
    }

}
