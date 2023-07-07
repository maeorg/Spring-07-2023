package ee.katrina.lemmikloomad.dto;

import ee.katrina.lemmikloomad.entity.Pet;
import lombok.Data;

import java.util.List;

@Data // tagataustal: @NoArgsConstructor @Getter @Setter
public class OwnerDTO {

    private String name;

    private List<Pet> pets;
}
