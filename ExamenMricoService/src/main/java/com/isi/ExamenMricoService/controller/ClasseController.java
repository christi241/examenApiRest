package com.isi.ExamenMricoService.controller;
import com.isi.ExamenMricoService.entities.Classe;
import com.isi.ExamenMricoService.repository.ClasseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClasseController {
    private final ClasseRepository classeRepository;

    public ClasseController(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    @GetMapping
    public List<Classe> getAll() {
        return classeRepository.findAll();
    }

    @PostMapping
    public Classe create(@RequestBody Classe classe) {
        return classeRepository.save(classe);
    }

    @GetMapping("/{id}")
    public Classe getById(@PathVariable Long id) {
        return classeRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Classe update(@PathVariable Long id, @RequestBody Classe updated) {
        return classeRepository.findById(id).map(c -> {
            c.setClassName(updated.getClassName());
            c.setDescription(updated.getDescription());
            return classeRepository.save(c);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        classeRepository.deleteById(id);
    }
}
