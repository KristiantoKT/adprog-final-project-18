package advprog.photonearby;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class PhotoNearbyTest {
    private static final String[] NORMAL_IMG_SET;

    static {
        NORMAL_IMG_SET = new String[]{"https://c1.staticflickr.com/4/3730/11061732505_99be9f6e03_b.jpg",
                                      "https://i0.wp.com/farm3.staticflickr.com/2890/11062301485_f4953afac3_z.jpg",
                                      "https://c1.staticflickr.com/8/7421/11098131876_d743d98a13_b.jpg",
                                      "https://c1.staticflickr.com/4/3714/11061791594_e5ca170069_b.jpg",
                                      "https://c1.staticflickr.com/6/5545/10903802176_6bfaa77587_b.jpg"};
    }

    private static final String[] LESS_THAN_5_IMG_SET;

    static {
        LESS_THAN_5_IMG_SET = new String[]{"https://c1.staticflickr.com/4/3730/11061732505_99be9f6e03_b.jpg",
                                           "https://i0.wp.com/farm3.staticflickr.com/2890/11062301485_f4953afac3_z.jpg",
                                           "https://c1.staticflickr.com/6/5545/10903802176_6bfaa77587_b.jpg"};
    }

    private static final String[] ERROR_MESSAGE = {"No image was taken near your location"};

    private PhotoNearby searchService;

    @Before
    public void setUp() {
        searchService = mock(PhotoNearby.class);
    }

    @Test
    public void testGet5ImageUrlThatAreTakenNearby() {
        when(searchService.searchImg("-6", "106")).thenReturn(NORMAL_IMG_SET);
        assertEquals(NORMAL_IMG_SET, searchService.searchImg("-6", "106"));
        verify(searchService).searchImg("-6", "106");
    }

    @Test
    public void testLessThan5ImageThatAreTakenNearby() {
        when(searchService.searchImg("-5", "100")).thenReturn(LESS_THAN_5_IMG_SET);
        assertEquals(LESS_THAN_5_IMG_SET, searchService.searchImg("-5", "100"));
        verify(searchService).searchImg("-5", "100");

    }

    @Test
    public void testNoImageThatAreTakenNearby() {
        when(searchService.searchImg("0", "0")).thenReturn(ERROR_MESSAGE);
        assertEquals(ERROR_MESSAGE, searchService.searchImg("0", "0"));
        verify(searchService).searchImg("0", "0");
    }

    @Test
    public void searchMethodIsWorking() {
        PhotoNearby test = new PhotoNearby();
        String[] result = test.searchImg("40.661292"," -73.968931");
        for (String img : result) {
            System.out.println(img);
        }
        assertNotNull(result);
    }
}
