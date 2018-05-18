package advprog.example.bot.hospital;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class HospitalTest {
    Hospital hospital;

    @BeforeEach
    void setUp() {
        hospital = new Hospital("Name Hospital", "Margonda", -6.0011, 108.0021,
                "(021) 12345678", "link.com", "This is example");
    }

    @Test
    void getName() {
        assertEquals("Name Hospital", hospital.getName());
    }

    @Test
    void getAddress() {
        assertEquals("Margonda", hospital.getAddress());
    }

    @Test
    void getLatitude() {
        assertEquals(-6.0011, hospital.getLatitude());
    }

    @Test
    void getLongitude() {
        assertEquals(108.0021, hospital.getLongitude());
    }

    @Test
    void getPhone() {
        assertEquals("(021) 12345678", hospital.getPhone());
    }

    @Test
    void getImageLink() {
        assertEquals("link.com", hospital.getImageLink());
    }

    @Test
    void getDescription() {
        assertEquals("This is example", hospital.getDescription());
    }
}