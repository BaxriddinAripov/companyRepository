package uz.pdp.appspringrestapi.service;

import org.springframework.stereotype.Service;
import uz.pdp.appspringrestapi.entity.Address;
import uz.pdp.appspringrestapi.entity.Department;
import uz.pdp.appspringrestapi.entity.Worker;
import uz.pdp.appspringrestapi.payload.ApiResponse;
import uz.pdp.appspringrestapi.payload.WorkerDto;
import uz.pdp.appspringrestapi.repository.AddressRepository;
import uz.pdp.appspringrestapi.repository.DepartmentRepository;
import uz.pdp.appspringrestapi.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    final
    WorkerRepository workerRepository;
    final
    AddressRepository addressRepository;
    final
    DepartmentRepository departmentRepository;

    public WorkerService(WorkerRepository workerRepository, AddressRepository addressRepository, DepartmentRepository departmentRepository) {
        this.workerRepository = workerRepository;
        this.addressRepository = addressRepository;
        this.departmentRepository = departmentRepository;
    }

    // GET ALL
    /**
     * Barcha Workers larni qaytarish
     * @return workers
     */
    public List<Worker> allWorkers(){
        return workerRepository.findAll();
    }

    // GET BY ID
    public Worker getWorker(Integer id){
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElse(null);
    }

    // CREAT
    public ApiResponse addWorker(WorkerDto workerDto){
        boolean exists = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (exists)
            return new ApiResponse("Bunday telefon raqam mavjud", false);
        // ADDRESS NI TEKSHIRISH
        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Bunday address mavjud emas", false);
        // DEPARTMENT NI TEKSHIRISH
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Bunday department mavjud emas", false);
        Worker worker = new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setDepartment(optionalDepartment.get());
        worker.setAddress(optionalAddress.get());
        workerRepository.save(worker);
        return new ApiResponse("Ma'lumot muvaffaqiyatli saqlandi", true);
    }

    // UPDATE BY ID
    /**
     * Worker ni ID orqali tahrirlash
     * @param id Integer
     * @param workerDto WorkerDto
     * @return ApiResponse
     */
    public ApiResponse editWorker(Integer id, WorkerDto workerDto){
        boolean exists = workerRepository.existsByIdNotAndPhoneNumber(id, workerDto.getPhoneNumber());
        if (exists)
            return new ApiResponse("Bunday id va phoneNumber mavjud", false);
        // WORKER TEKSHIRISH
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent())
            return new ApiResponse("Bunday worker mavjud emas", false);
        // ADDRESS NI TEKSHIRISH
        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Bunday address mavjud emas", false);
        // DEPARTMENT NI TEKSHIRISH
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Bunday department mavjud emas", false);
        Worker worker = optionalWorker.get();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setAddress(optionalAddress.get());
        worker.setDepartment(optionalDepartment.get());
        workerRepository.save(worker);
        return new ApiResponse("Malumot muvaffaqiyatli o'zgartirildi", true);
    }

    // DELETE BY ID
    /**
     * Worker ni ID orqali o'chirish
     * @param id Integer
     * @return ApiResponse
     */
    public ApiResponse deleteWorker(Integer id){
        try {
            workerRepository.deleteById(id);
            return new ApiResponse("Ma'lumot muvaffaqiyatli o'chirildi", true);
        }catch (Exception e){
            return new ApiResponse("Xatolik!!!", false);
        }
    }
}

