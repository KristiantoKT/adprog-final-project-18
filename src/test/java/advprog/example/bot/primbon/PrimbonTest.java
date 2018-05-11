package advprog.example.bot.primbon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.example.bot.EventTestUtil;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
public class PrimbonTest {
    Primbon primbon;

    @BeforeEach
    void setUp(){
        primbon = new Primbon("2018-05-11");
    }

    @Test
    void getTanggal(){
        assertEquals("2018-05-11", primbon.getTanggal());
    }

    @Test
    void setTanggal(){
        primbon.setTanggal("2018-05-12");
        assertEquals("2018-05-11", primbon.getTanggal());
    }

    @Test
    void tanggalJadwal(){
        assertEquals("Jumat Legi", primbon.tanggalJawa("2018-05-11"));
    }

    @Test
    void weton(){
        assertEquals("Pon", primbon.weton(0));
    }

    @Test
    void hari(){
        assertEquals("Minggu", primbon.hari(0));
    }
}
