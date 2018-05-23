package advprog.example.bot.cgv;


public class ChangeCinema implements State {

    CgvMetadata cgvMetadata;
    private String url;

    public ChangeCinema(String url) {
        this.url = url;
        cgvMetadata = new CgvMetadata(this, this.url);

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
