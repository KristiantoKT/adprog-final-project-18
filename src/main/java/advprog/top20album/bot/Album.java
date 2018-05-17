package advprog.top20album.bot;

public class Album {
    private int position;
    private String name;
    private double rating;
    private int price;

    public Album(int position, String name, double rating, int price) {
        this.position = position;
        this.name = name;
        this.rating = rating;
        this.price = price;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return position + " - " + name + " - " + rating + " (" + price +" IDR)";
    }
}
