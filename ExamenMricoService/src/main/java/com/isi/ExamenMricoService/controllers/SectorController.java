package com.isi.ExamenMricoService.controllers;

import com.isi.ExamenMricoService.dto.SectorDto;
import com.isi.ExamenMricoService.services.SectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/sectors")
@RequiredArgsConstructor
@Tag(name = "Secteurs", description = "Gestion des secteurs d'enseignement (filières)")
public class SectorController {

    private final SectorService sectorService;

    @Operation(
        summary = "Récupérer tous les secteurs",
        description = "Retourne la liste complète de tous les secteurs d'enseignement"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des secteurs récupérée avec succès")
    })
    @GetMapping
    public List<SectorDto.Response> getAll() {
        return sectorService.getAll();
    }

    @Operation(
        summary = "Récupérer un secteur par ID",
        description = "Retourne les détails d'un secteur spécifique"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Secteur trouvé"),
        @ApiResponse(responseCode = "404", description = "Secteur non trouvé",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/{id}")
    public SectorDto.Response getById(
        @Parameter(description = "Identifiant unique du secteur", required = true, example = "1")
        @PathVariable Long id
    ) {
        return sectorService.getById(id);
    }

    @Operation(
        summary = "Créer un nouveau secteur",
        description = "Crée un nouveau secteur d'enseignement avec un nom unique"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Secteur créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "409", description = "Un secteur avec ce nom existe déjà",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping
    public ResponseEntity<SectorDto.Response> create(@Valid @RequestBody SectorDto.CreateRequest request) {
        SectorDto.Response created = sectorService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @Operation(
        summary = "Mettre à jour un secteur",
        description = "Met à jour les informations d'un secteur existant"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Secteur mis à jour avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "404", description = "Secteur non trouvé",
                    content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "409", description = "Un autre secteur avec ce nom existe déjà",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<SectorDto.Response> update(
        @Parameter(description = "Identifiant unique du secteur", required = true, example = "1")
        @PathVariable Long id,
        @Valid @RequestBody SectorDto.UpdateRequest request
    ) {
        SectorDto.Response updated = sectorService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @Operation(
        summary = "Supprimer un secteur",
        description = "Supprime un secteur et toutes ses classes associées (cascade)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Secteur supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Secteur non trouvé",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @Parameter(description = "Identifiant unique du secteur", required = true, example = "1")
        @PathVariable Long id
    ) {
        sectorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
