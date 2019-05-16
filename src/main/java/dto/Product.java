package dto;

public class Product {
    private int id;
    private String name;
    private double price;
    private Category category;
    private String url;

    public Product(int id, String name, Category category, double price, String url) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public  static  Product getDummyProduct()
    {
        return new Product(0,"Dummy ProductLocationProvider", Category.getDummyCategory(), 0.0,"");
    }
}
