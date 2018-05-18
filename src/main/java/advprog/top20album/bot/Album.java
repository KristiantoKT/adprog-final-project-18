package advprog.top20album.bot;

public class Album {
    private int position;
    private String name;
    private String rating;
    private String price;

    public Album(int position, String name, String rating, String price) {
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return position + " - " + name + " - " + rating + " (" + price + " IDR)";
    }
}
