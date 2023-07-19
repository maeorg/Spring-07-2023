package ee.katrina.webshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders") // PostgreSQL sees on "order" ja "user" reserveeritud, ei saa neid kasutada tabeli nimedena
@SequenceGenerator(name = "seq", initialValue = 8522086, allocationSize = 1)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq") // Et ei tuleks 1,2,3,4,5 vaid 8522086
    private Long id;
    private Date creationDate;
    private String paymentState;
    private double totalSum;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<OrderRow> orderRow;
    @ManyToOne
    private Person person;
}
