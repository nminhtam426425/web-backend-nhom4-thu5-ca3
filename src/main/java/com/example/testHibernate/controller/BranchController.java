package com.example.testHibernate.controller;

import com.example.testHibernate.entity.Branches;
import com.example.testHibernate.service.BranchesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branches")
public class BranchController {
    @Autowired
    private BranchesServices services;

    @GetMapping
    public List<Branches> getAll(){
        return services.getAll();
    }

    @PostMapping
    public Branches create(@RequestBody Branches b){
        return services.create(b);
    }

    @PutMapping("/{id}")
    public Branches update(@PathVariable Integer id,@RequestBody Branches b){
        return  services.update(id,b);
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        services.delete(id);
        return "Deleted";
    }

    @PutMapping("/hide/{id}")
    public String hide(@PathVariable Integer id){
        services.hide(id);
        return "Hidden";
    }
}
