package uz.pdp.appspringrestapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringrestapi.entity.Worker;
import uz.pdp.appspringrestapi.payload.ApiResponse;
import uz.pdp.appspringrestapi.payload.WorkerDto;
import uz.pdp.appspringrestapi.service.WorkerService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class WorkerController {

    final
    WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    // GET ALL
    /**
     * Barcha Workers larni qaytarish
     * @return workers
     */
    @GetMapping("api/worker")
    public ResponseEntity<List<Worker>> allWorkers(){
        List<Worker> workers = workerService.allWorkers();
        return ResponseEntity.ok(workers);
    }

    // GET BY ID
    @GetMapping("api/worker/{id}")
    public ResponseEntity<Worker> getWorker(@PathVariable Integer id){
        Worker worker = workerService.getWorker(id);
        return ResponseEntity.ok(worker);
    }

    // CREAT
    /**
     * Worker qo'shish uchun method
     * @param workerDto WorkerDto
     * @return ApiResponse
     */
    @PostMapping("api/worker")
    public ResponseEntity<ApiResponse> addWorker(@Valid @RequestBody WorkerDto workerDto){
        ApiResponse apiResponse = workerService.addWorker(workerDto);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }

    // UPDATE BY ID
    /**
     * Worker ni ID orqali tahrirlash
     * @param id Integer
     * @param workerDto WorkerDto
     * @return ApiResponse
     */
    @PutMapping("api/worker/{id}")
    public ResponseEntity<ApiResponse> editWorker(@PathVariable Integer id, @Valid @RequestBody WorkerDto workerDto){
        ApiResponse apiResponse = workerService.editWorker(id, workerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }

    // DELETE BY ID
    /**
     * Worker ni ID orqali o'chirish
     * @param id Integer
     * @return ApiResponse
     */
    @DeleteMapping("api/worker{id}")
    public ResponseEntity<ApiResponse> deleteWorker(@PathVariable Integer id){
        ApiResponse apiResponse = workerService.deleteWorker(id);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }
}
