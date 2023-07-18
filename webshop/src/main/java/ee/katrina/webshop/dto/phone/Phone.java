package ee.katrina.webshop.dto.phone;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Phone {
    public int id;
    public String title;
    public String description;
    public int price;
    public double discountPercentage;
    public double rating;
    public int stock;
    public String brand;
    public String category;
    public String thumbnail;
    public ArrayList<String> images;
}
