package com.example.demo.service;

import com.example.demo.model.Worker;
import com.example.demo.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class WorkerServiceImpl implements WorkerService{
    @Autowired
    private WorkerRepository workerRepository;
    @Override
    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }
    @Override
    public void saveWorker(Worker worker){
        this.workerRepository.save(worker);
    }
    @Override
    public Worker getWorkerById(long id){
        Optional<Worker> optional = workerRepository.findById(id);
        Worker worker = null;
        if(optional.isPresent()){
            worker = optional.get();
        }else {
            throw new RuntimeException("Worker not found for id :: " + id);
        }
        return worker;
    }

    @Override
    public void deleteWorkerById(long id) {
        this.workerRepository.deleteById(id);
    }

    @Override
    public Page<Worker> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.workerRepository.findAll(pageable);
    }
}
