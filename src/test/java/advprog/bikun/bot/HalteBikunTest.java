package advprog.bikun.bot;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HalteBikunTest {
    HalteBikun[] halteBikuns;

    @BeforeEach
    void setUp() throws Exception {
        String path = "./src/main/java/advprog/bikun/bot/halte-bikun.json";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        ObjectMapper objectMapper = new ObjectMapper();
        halteBikuns = objectMapper.readValue(bufferedReader, HalteBikun[].class);
    }

    @Test
    void testHalteBikunFunctionality() {
        HalteBikun halteBikun = halteBikuns[0];
        assertEquals("Halte Fisip UI", halteBikun.getNama());
        assertEquals(-6.361853, halteBikun.getLatitude());
        assertEquals(106.830153, halteBikun.getLongitude());
        assertEquals("https://static.panoramio.com.storage.googleapis.com/"
                + "photos/large/54877172.jpg", halteBikun.getImgUrl());
        assertNotNull(halteBikun.getJadwal());
    }


}
