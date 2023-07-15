package ee.katrina.webshop.dto;

import lombok.Data;

@Data
public class Photo {
    public int albumId;
    public int id;
    public String title;
    public String url;
    public String thumbnailUrl;
}
