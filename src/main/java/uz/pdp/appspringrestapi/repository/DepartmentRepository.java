package uz.pdp.appspringrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appspringrestapi.entity.Company;
import uz.pdp.appspringrestapi.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    boolean existsByNameAndCompanyId(String name, Company companyId);

    boolean existsByNameAndCompanyIdNot(String name, Company companyId);
}
