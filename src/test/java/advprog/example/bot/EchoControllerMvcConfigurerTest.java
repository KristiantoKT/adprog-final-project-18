package advprog.example.bot;

import org.junit.Test;

public class EchoControllerMvcConfigurerTest {


    @Test(expected = NullPointerException.class)
    public void addResourceHandlerTestFail() {
        EchoControllerMvcConfigurer tmp = new EchoControllerMvcConfigurer();
        tmp.addResourceHandlers(null);
    }

}
