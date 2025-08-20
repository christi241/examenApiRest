package com.isi.ExamenMricoService.dto;

import com.isi.ExamenMricoService.entities.Sector;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTOs (Data Transfer Objects) pour l'entité Sector.
 */
public final class SectorDto {

    private SectorDto() {
        // util class
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Requête de création d'un secteur")
    public static class CreateRequest {
        @Schema(description = "Nom du secteur", example = "Informatique", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(max = 120)
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Requête de mise à jour d'un secteur")
    public static class UpdateRequest {
        @Schema(description = "Nouveau nom du secteur", example = "Génie Logiciel", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(max = 120)
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Réponse contenant les informations d'un secteur")
    public static class Response {
        @Schema(description = "Identifiant unique du secteur", example = "1")
        private Long id;
        @Schema(description = "Nom du secteur", example = "Informatique")
        private String name;


    }
}
