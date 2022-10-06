package uz.pdp.appspringrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appspringrestapi.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    boolean existsByStreet(String street);

    boolean existsByStreetAndIdNot(String street, Integer id);
}
