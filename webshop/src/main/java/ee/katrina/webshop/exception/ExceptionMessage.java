package ee.katrina.webshop.exception;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class ExceptionMessage {

    private Date date;
    private String message;
    private int httpStatusCode;

}
