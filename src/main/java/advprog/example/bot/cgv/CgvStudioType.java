package advprog.example.bot.cgv;

public class CgvStudioType implements State {
    private State cinema;

    public void setState(State state) {
        this.cinema = state;
    }

    public State getState() {
        return this.cinema;
    }

    @Override
    public String cinemaName() {
        return this.cinema.cinemaName();
    }

    @Override
    public String cgvGoldClass() {
        return this.cinema.cgvGoldClass();
    }

    @Override
    public String cgvRegular2d() {
        return this.cinema.cgvRegular2d();
    }

    @Override
    public String cgv4DxCinema() {
        return this.cinema.cgv4DxCinema();
    }

    @Override
    public String cgvVelvet() {
        return this.cinema.cgvVelvet();
    }

    @Override
    public String cgvSweetBox() {
        return this.cinema.cgvSweetBox();
    }
}
