package enterkomputer.utilities;

import java.io.IOException;
import java.util.stream.Collectors;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class KategoriScrapper implements ScrapperInterface {

    public static void main(String[]args) throws IOException {
        KategoriScrapper kategoriScrapper = new KategoriScrapper();
        String urlEnterKomputer = "https://www.enterkomputer.com/";
        System.out.println(kategoriScrapper.scrapperWebsite(urlEnterKomputer));
    }


    public KategoriScrapper() {
        String allListKategori = "Processor\n"
                + "<motherboard>\n"
                + "harddisk\n"
                + "SSD\n"
                + "VGA Card\n"
                + "Power Supply\n"
                + "Casing\n"
                + "LCD\n"
                + "Optical Drive\n"
                + "Keyboard & Mouse\n"
                + "Software & OS\n"
                + "Cooler & Fan\n"
                + "Flashdisk\n"
                + "Memory Card\n"
                + "UPS & Stabilizer\n"
                + "Networking\n"
                + "Modem Portable (GSM & CDMA)\n"
                + "AIO PC & PC Branded\n"
                + "Server\n"
                + "NAS\n"
                + "Office\n"
                + " Accessories Powerbank & Mobile Adaptor / "
                + "Charger Enclosure/Docking/Harddisk Case Protector Cable / Converter / KVM "
                + "/ Splitter Thermal Pasta Webcam USB HUB,Card Reader,PCI "
                + "/ USB Converter,Bluetooth Dongle "
                + "TV Tuner & TV Box & MP3 Gamepad, Steering, Presenter, Gaming Glasses "
                + "Function Panel & Front Panel Converter Neon CCFL\n"
                + "Audio Speaker Headset Sound Card\n"
                + "Printer & Catridge Printer Cartridge & Toner\n"
                + "NoteBook & Accessories Notebook & Ultrabook Notebook Cooler,"
                + " Battery, Adaptor, Sparepart, Accessories\n"
                + "Tablet & Smartphone Tablet Smartphone Tempered Glass & Acc\n"
                + "Digital Drawing Tablet\n"
                + "Media Player\n"
                + "Projector\n"
                + "Drone";
    }

    public String scrapperWebsite(String urlEnterKomputer) throws IOException {
        try {
            Document document = Jsoup.connect(urlEnterKomputer).get();
            Elements headerNoHov = document.select("li.main-kata");
            Elements headerHov = document.select("li.fix-hov");
            return "List Kategori Enter Komputer:\n"
                    + headerNoHov.stream().map(katName -> headerRawUtils(katName))
                    .collect(Collectors.joining("\n"));
        } catch (HttpStatusException e) {
            return "Url 404 Not Found";
        }
    }

    private String headerRawUtils(Element header) {
        String tmp = header.select("li.main-kata").text();
        return header.text();
    }
}
