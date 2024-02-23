package com.example.demo.service;

import com.example.demo.model.Worker;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkerService {
    List<Worker> getAllWorkers();
    void saveWorker(Worker worker);
    Worker getWorkerById(long id);
    void deleteWorkerById(long id);
    Page<Worker> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
