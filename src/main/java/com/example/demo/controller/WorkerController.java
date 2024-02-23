package com.example.demo.controller;

import com.example.demo.model.Worker;
import com.example.demo.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WorkerController {
    @Autowired
    private WorkerService workerService;
    // display list of workers
    @GetMapping("/home")
    public String viewHomePage(Model model){
        return findPaginated(1, "firstName", "asc", model);
    }
    @GetMapping("/showNewWorkerForm")
    public String showNewWorkerForm(Model model){
        //create model attribute to bind form data
        Worker worker = new Worker();
        model.addAttribute("worker", worker);
        return "new_worker";
    }
    @PostMapping("/saveWorker")
    public String saveWorker(@ModelAttribute("worker") Worker worker){
        //save worker to database
        workerService.saveWorker(worker);
        return "redirect:/home";
    }
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model){
        //get worker from the service
        Worker worker = workerService.getWorkerById(id);
        //set worker as a model attribute to pre-populate the form
        model.addAttribute("worker", worker);
        return "update_worker";
    }
    @GetMapping("/deleteWorker/{id}")
    public String deleteWorker(@PathVariable(value = "id") long id){
        //call delete worker method
        this.workerService.deleteWorkerById(id);
        return "redirect:/home";
    }

    // /page/1?sortField=name&sortDir=asc
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model){
        int pageSize = 5;

        Page<Worker> page = workerService.findPaginated(pageNo,pageSize, sortField, sortDir);
        List<Worker> listWorker = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listWorkers", listWorker);
        return "index";
    }
}
