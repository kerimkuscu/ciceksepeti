package dto;

/**
 * Created by emre on 22/03/2019
 */
public class Interaction {
    private int id;
    private int userId;
    private int productId;

    public Interaction(int id, int userId, int productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }
    public Interaction() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
