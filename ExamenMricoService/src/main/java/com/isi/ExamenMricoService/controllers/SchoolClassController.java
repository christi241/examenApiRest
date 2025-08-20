package com.isi.ExamenMricoService.controllers;

import com.isi.ExamenMricoService.dto.SchoolClassDto;
import com.isi.ExamenMricoService.services.SchoolClassService;
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

/**
 * REST controller pour la gestion des classes.
 * Base path: /api/classes (le context-path /api est défini dans la configuration)
 */
@RestController
@RequestMapping("/classes")
@RequiredArgsConstructor
@Tag(name = "Classes", description = "Gestion des classes rattachées aux secteurs")
public class SchoolClassController {

    private final SchoolClassService classService;

    @Operation(
        summary = "Récupérer toutes les classes",
        description = "Retourne la liste des classes, avec possibilité de filtrer par secteur"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des classes récupérée avec succès"),
        @ApiResponse(responseCode = "404", description = "Secteur non trouvé (si sectorId fourni)",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping
    public List<SchoolClassDto.Response> getAll(
        @Parameter(description = "ID du secteur pour filtrer les classes (optionnel)", example = "1")
        @RequestParam(value = "sectorId", required = false) Long sectorId
    ) {
        if (sectorId != null) {
            return classService.getAllBySector(sectorId);
        }
        return classService.getAll();
    }

    @Operation(
        summary = "Récupérer une classe par ID",
        description = "Retourne les détails d'une classe spécifique avec les informations du secteur"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Classe trouvée"),
        @ApiResponse(responseCode = "404", description = "Classe non trouvée",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/{id}")
    public SchoolClassDto.Response getById(
        @Parameter(description = "Identifiant unique de la classe", required = true, example = "1")
        @PathVariable Long id
    ) {
        return classService.getById(id);
    }

    @Operation(
        summary = "Créer une nouvelle classe",
        description = "Crée une nouvelle classe rattachée à un secteur existant"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Classe créée avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "404", description = "Secteur non trouvé",
                    content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "409", description = "Une classe avec ce nom existe déjà dans ce secteur",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping
    public ResponseEntity<SchoolClassDto.Response> create(
        @Valid @RequestBody SchoolClassDto.CreateRequest request
    ) {
        SchoolClassDto.Response created = classService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @Operation(
        summary = "Mettre à jour une classe",
        description = "Met à jour les informations d'une classe existante, y compris son rattachement à un secteur"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Classe mise à jour avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "404", description = "Classe ou secteur non trouvé",
                    content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "409", description = "Une autre classe avec ce nom existe déjà dans ce secteur",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<SchoolClassDto.Response> update(
        @Parameter(description = "Identifiant unique de la classe", required = true, example = "1")
        @PathVariable Long id,
        @Valid @RequestBody SchoolClassDto.UpdateRequest request
    ) {
        SchoolClassDto.Response updated = classService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @Operation(
        summary = "Supprimer une classe",
        description = "Supprime définitivement une classe"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Classe supprimée avec succès"),
        @ApiResponse(responseCode = "404", description = "Classe non trouvée",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @Parameter(description = "Identifiant unique de la classe", required = true, example = "1")
        @PathVariable Long id
    ) {
        classService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
