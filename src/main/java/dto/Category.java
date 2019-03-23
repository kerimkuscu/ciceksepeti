package dto;

/**
 * Created by emre on 23/03/2019
 */
public class Category {
    private int id;
    private String category;


    public Category(int id, String category) {
        this.id = id;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public  static  Category getDummyCategory()
    {
        return new Category(0,"Kategorisiz");
    }
}
