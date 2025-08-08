package com.isi.ExamenMricoService.controller;
import com.isi.ExamenMricoService.entities.Filliere;
import com.isi.ExamenMricoService.repository.FiliereRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filieres")
public class FiliereController {
    private final FiliereRepository filiereRepository;

    public FiliereController(FiliereRepository filiereRepository) {
        this.filiereRepository = filiereRepository;
    }

    @GetMapping
    public List<Filliere> getAll() {
        return filiereRepository.findAll();
    }

    @PostMapping
    public Filliere create(@RequestBody Filliere filiere) {
        return filiereRepository.save(filiere);
    }

    @GetMapping("/{id}")
    public Filliere getById(@PathVariable Long id) {
        return filiereRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Filliere update(@PathVariable Long id, @RequestBody Filliere updated) {
        return filiereRepository.findById(id).map(f -> {
            f.setName(updated.getName());
            return filiereRepository.save(f);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        filiereRepository.deleteById(id);
    }
}