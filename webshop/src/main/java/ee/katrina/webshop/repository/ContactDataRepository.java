package ee.katrina.webshop.repository;

import ee.katrina.webshop.entity.ContactData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDataRepository extends JpaRepository<ContactData, Long> {
}
