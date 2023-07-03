package ee.katrina.lemmikloomad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {

    private String name;
    private String telephone;
    private String address;
    private String height;
    private String weight;

}
