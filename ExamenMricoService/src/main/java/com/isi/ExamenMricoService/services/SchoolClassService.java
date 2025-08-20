package com.isi.ExamenMricoService.services;

import com.isi.ExamenMricoService.dto.SchoolClassDto;
import com.isi.ExamenMricoService.entities.SchoolClass;
import com.isi.ExamenMricoService.entities.Sector;
import com.isi.ExamenMricoService.mappers.SchoolClassMapper;
import com.isi.ExamenMricoService.repositories.SchoolClassRepository;
import com.isi.ExamenMricoService.repositories.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchoolClassService {

    private final SchoolClassRepository classRepository;
    private final SectorRepository sectorRepository;
    private final SchoolClassMapper schoolClassMapper;

    // ---------- Read ----------

    @Transactional(readOnly = true)
    public List<SchoolClassDto.Response> getAll() {
        return classRepository.findAll()
            .stream()
            .map(schoolClassMapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<SchoolClassDto.Response> getAllBySector(Long sectorId) {
        ensureSectorExists(sectorId);
        return classRepository.findAllBySector_Id(sectorId)
            .stream()
            .map(schoolClassMapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public SchoolClassDto.Response getById(Long id) {
        SchoolClass entity = getClassOrThrow(id);
        return schoolClassMapper.toResponse(entity);
    }

    // ---------- Create ----------

    @Transactional
    public SchoolClassDto.Response create(SchoolClassDto.CreateRequest request) {
        Sector sector = getSectorOrThrow(request.getSectorId());

        // Validation: nom unique dans le secteur (case-insensitive)
        if (classRepository.existsByClassNameIgnoreCaseAndSector_Id(request.getClassName(), sector.getId())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Class with the same name already exists in this sector"
            );
        }

        SchoolClass toSave = schoolClassMapper.toEntity(request);
        toSave.setSector(sector);

        SchoolClass saved = classRepository.save(toSave);
        return schoolClassMapper.toResponse(saved);
    }

    // ---------- Update ----------

    @Transactional
    public SchoolClassDto.Response update(Long id, SchoolClassDto.UpdateRequest request) {
        SchoolClass existing = getClassOrThrow(id);
        Sector newSector = getSectorOrThrow(request.getSectorId());

        // Validation: nom unique dans le secteur ciblé (autre que l'entité courante)
        Optional<SchoolClass> byNameInSector =
            classRepository.findByClassNameIgnoreCaseAndSector_Id(request.getClassName(), newSector.getId());

        if (byNameInSector.isPresent() && !byNameInSector.get().getId().equals(id)) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Another class with the same name already exists in this sector"
            );
        }

        schoolClassMapper.updateEntity(request, existing);
        if (existing.getSector() == null || !existing.getSector().getId().equals(newSector.getId())) {
            existing.setSector(newSector);
        }

        SchoolClass saved = classRepository.save(existing);
        return schoolClassMapper.toResponse(saved);
    }

    // ---------- Delete ----------

    @Transactional
    public void delete(Long id) {
        SchoolClass entity = getClassOrThrow(id);
        classRepository.delete(entity);
    }

    // ---------- Helpers ----------

    private SchoolClass getClassOrThrow(Long id) {
        return classRepository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found"));
    }

    private void ensureSectorExists(Long sectorId) {
        if (!sectorRepository.existsById(sectorId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sector not found");
        }
    }

    private Sector getSectorOrThrow(Long id) {
        return sectorRepository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Sector not found"));
    }
}
