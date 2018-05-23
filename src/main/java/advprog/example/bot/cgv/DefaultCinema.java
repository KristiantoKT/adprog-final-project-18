package advprog.example.bot.cgv;


public class DefaultCinema implements State {

    private final String cinemaUrl = "https://www.cgv.id/en/schedule/cinema/002";
    private final CgvMetadata cgvMetadata = new CgvMetadata(this,cinemaUrl);

    public DefaultCinema() {
        cgvMetadata.groupingMovieByClass();
        cgvMetadata.groupingMovieByDimension();
    }

    @Override
    public String cinemaName() {
        return cgvMetadata.getCinemaName();
    }

    @Override
    public String cgvGoldClass() {
        return cgvMetadata.cgvGoldClass();
    }

    @Override
    public String cgvRegular2d() {
        return cgvMetadata.cgvRegular2d();
    }

    @Override
    public String cgv4DxCinema() {
        return cgvMetadata.cgv4DxCinema();
    }

    @Override
    public String cgvVelvet() {
        return cgvMetadata.cgvVelvet();
    }

    @Override
    public String cgvSweetBox() {
        return cgvMetadata.cgvSweetBox();
    }

}
