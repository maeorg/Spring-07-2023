package ee.katrina.webshop.dto.security;

import lombok.Data;

@Data
public class LoginData {

    private String email;
    private String password;
    private String personalCode;

}
