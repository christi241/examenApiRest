package com.isi.ExamenMricoService.services;

import com.isi.ExamenMricoService.dto.SectorDto;
import com.isi.ExamenMricoService.entities.Sector;
import com.isi.ExamenMricoService.mappers.SectorMapper;
import com.isi.ExamenMricoService.repositories.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Service métier pour la gestion des Sectors (filières).
 * Fournit des opérations CRUD avec quelques validations de base.
 */
@Service
@RequiredArgsConstructor
public class SectorService {

    private final SectorRepository sectorRepository;
    private final SectorMapper sectorMapper;

    // ---------- Read ----------

    @Transactional(readOnly = true)
    public List<SectorDto.Response> getAll() {
        return sectorRepository.findAll()
            .stream()
            .map(sectorMapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public SectorDto.Response getById(Long id) {
        Sector sector = getSectorOrThrow(id);
        return sectorMapper.toResponse(sector);
    }

    // ---------- Create ----------

    @Transactional
    public SectorDto.Response create(SectorDto.CreateRequest request) {
        // Validation: nom unique (case-insensitive)
        if (sectorRepository.existsByNameIgnoreCase(request.getName())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Sector with the same name already exists"
            );
        }

        Sector toSave = sectorMapper.toEntity(request);
        Sector saved = sectorRepository.save(toSave);
        return sectorMapper.toResponse(saved);
    }

    // ---------- Update ----------

    @Transactional
    public SectorDto.Response update(Long id, SectorDto.UpdateRequest request) {
        Sector sector = getSectorOrThrow(id);

        // Validation: nom unique (case-insensitive) pour un autre secteur
        Optional<Sector> existingByName = sectorRepository.findByNameIgnoreCase(request.getName());
        if (existingByName.isPresent() && !existingByName.get().getId().equals(id)) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Another sector with the same name already exists"
            );
        }

        sectorMapper.updateEntity(request, sector);
        Sector saved = sectorRepository.save(sector);
        return sectorMapper.toResponse(saved);
    }

    // ---------- Delete ----------

    @Transactional
    public void delete(Long id) {
        Sector sector = getSectorOrThrow(id);
        sectorRepository.delete(sector);
        // Grâce au cascade = ALL sur Sector.classes, les classes rattachées seront supprimées.
    }

    // ---------- Helpers ----------

    private Sector getSectorOrThrow(Long id) {
        return sectorRepository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Sector not found"));
    }
}
