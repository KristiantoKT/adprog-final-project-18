package advprog.example.bot.controller;

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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class EchoControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private EchoController echoController;

    @Test
    void testContextLoads() {
        assertNotNull(echoController);
    }

    @Test
    void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/echo Lorem Ipsum");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("Lorem Ipsum", reply.getText());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        echoController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }

    @Test
    void testKategoriEnterKomputer() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/enterkomputer listkategori");
        TextMessage reply = echoController.handleTextMessageEvent(event);
        String expectedOut = "Kategori yang ada di EnterKomputer adalah sebagai berikut"
                + " untuk menggunakan bot harap gunakan kata yang ada di dalam tanda '<~>':\n"
                + "Processor <processor>\n"
                + "Motherboard <motherboard>\n"
                + "Harddisk <harddisk>\n"
                + "SSD <ssd>\n"
                + "RAM:\n"
                + "PC RAM <memoryram>\n"
                + "Notebook RAM <memoryramnotebook>\n"
                + "VGA Card <vga>\n"
                + "Power Supply <psu>\n"
                + "Casing PC <casing>\n"
                + "LCD <lcd>\n"
                + "Optical Drive <optical>\n"
                + "Keyboard & Mouse <keyboard>\n"
                + "Software & OS <software>\n"
                + "Cooler & Fan  <coolerfan>\n"
                + "Flashdisk <flashdisk>\n"
                + "Memory Card<memorycard>\n"
                + "UPS & Stabilizer <ups>\n"
                + "Networking <networking>\n"
                + "Modem Portable (GSM & CDMA) <modem>\n"
                + "AIO PC & PC Branded <allinone>\n"
                + "Server <server>\n"
                + "NAS <nas>\n"
                + "Office <office>\n"
                + "Powerbank & Mobile Adaptor / Charger <powerbank>\n"
                + "Enclosure/Docking/Harddisk Case Protector <casinghdd>\n"
                + "Cable / Converter / KVM / Splitter <cable>\n"
                + "Thermal Pasta <thermalpasta>\n"
                + "Webcam <webcam>\n"
                + "USB HUB,Card Reader,PCI / USB Converter,Bluetooth Dongle <usbhub>\n"
                + "TV Tuner & TV Box & MP3 <tvtuner>\n"
                + "Gamepad, Steering, Presenter, Gaming Glasses <gamepad>\n"
                + "Function Panel & Front Panel Converter<functionpanel>"
                + "Neon CCFL <ccfl>\n"
                + "Speaker <speaker>\n"
                + "Headset <headset>\n"
                + "Sound Card <sound card>\n"
                + "Printer <printer>\n"
                + "Cartridge & Toner <cartridge>\n"
                + "Notebook & Ultrabook <notebook>\n"
                + "Notebook Cooler, Battery, Adaptor, Sparepart, Accessories <notebookacc>\n"
                + "Tablet Smartphone Tempered Glass & Acc <tempered>\n"
                + "Digital Drawing Tablet <drawing>\n"
                + "Media Player <mediaplayer>\n"
                + "Projector <projector>\n"
                + "Drone <drone>";
        assertEquals(reply.getText(),expectedOut);
    }
}