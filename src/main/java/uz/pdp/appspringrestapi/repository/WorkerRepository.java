package uz.pdp.appspringrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appspringrestapi.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByIdNotAndPhoneNumber(Integer id, String phoneNumber);
}
