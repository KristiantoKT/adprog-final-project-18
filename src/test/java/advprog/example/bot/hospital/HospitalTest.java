package advprog.example.bot.hospital;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;

class HospitalTest {
    Hospital[] hospitals;

    @BeforeEach
    void setUp() throws Exception {
//        hospital = new Hospital("Name Hospital", "Margonda", -6.0011, 108.0021,
//                "(021) 12345678", "link.com", "This is example");
        String path = "./src/main/java/advprog/example/bot/hospital/hospital-list.json";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        ObjectMapper objectMapper = new ObjectMapper();
        hospitals = objectMapper.readValue(bufferedReader, Hospital[].class);
    }

    @Test
    void testHospitalFunctionality() {
        Hospital hospital = hospitals[0];
        assertEquals("RSUD Tarakan", hospital.getName());
        assertEquals("Jl. Kyai Caringin No. 7, Cideng, Gambir, Jakarta Pusat, 10150",
                hospital.getAddress());
        assertEquals(-6.17156, hospital.getLatitude());
        assertEquals(106.810219, hospital.getLongitude());
        assertEquals("(021) 3503003", hospital.getPhone());
        assertTrue(hospital.getImageLink().contains("tarakan"));
        assertTrue(hospital.getDescription().contains("Tarakan"));
    }
//
//    @Test
//    void getName() {
//        assertEquals("Name Hospital", hospital.getName());
//    }
//
//    @Test
//    void getAddress() {
//        assertEquals("Margonda", hospital.getAddress());
//    }
//
//    @Test
//    void getLatitude() {
//        assertEquals(-6.0011, hospital.getLatitude());
//    }
//
//    @Test
//    void getLongitude() {
//        assertEquals(108.0021, hospital.getLongitude());
//    }
//
//    @Test
//    void getPhone() {
//        assertEquals("(021) 12345678", hospital.getPhone());
//    }
//
//    @Test
//    void getImageLink() {
//        assertEquals("link.com", hospital.getImageLink());
//    }
//
//    @Test
//    void getDescription() {
//        assertEquals("This is example", hospital.getDescription());
//    }
}