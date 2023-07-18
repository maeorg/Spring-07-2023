package ee.katrina.webshop.dto.phone;

import ee.katrina.webshop.dto.phone.Phone;
import lombok.Data;

import java.util.ArrayList;

@Data
public class PhoneResponse {
    public ArrayList<Phone> products;
    public int total;
    public int skip;
    public int limit;
}
