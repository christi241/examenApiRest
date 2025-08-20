package com.isi.ExamenMricoService.dto;

import com.isi.ExamenMricoService.entities.SchoolClass;
import com.isi.ExamenMricoService.entities.Sector;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTOs (Data Transfer Objects) pour l'entité SchoolClass.
 */
public final class SchoolClassDto {

    private SchoolClassDto() {
        // util class
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Requête de création d'une classe")
    public static class CreateRequest {
        @Schema(description = "Nom de la classe", example = "L3 GL", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(max = 120)
        private String className;

        @Schema(description = "Description de la classe", example = "Licence 3 Génie Logiciel")
        @Size(max = 255)
        private String description;

        @Schema(description = "Identifiant du secteur auquel rattacher la classe", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        private Long sectorId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Requête de mise à jour d'une classe")
    public static class UpdateRequest {
        @Schema(description = "Nouveau nom de la classe", example = "M1 GL", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(max = 120)
        private String className;

        @Schema(description = "Nouvelle description de la classe", example = "Master 1 Génie Logiciel")
        @Size(max = 255)
        private String description;

        @Schema(description = "Identifiant du secteur auquel rattacher la classe", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        private Long sectorId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Réponse contenant les informations d'une classe")
    public static class Response {
        @Schema(description = "Identifiant unique de la classe", example = "1")
        private Long id;
        @Schema(description = "Nom de la classe", example = "L3 GL")
        private String className;
        @Schema(description = "Description de la classe", example = "Licence 3 Génie Logiciel")
        private String description;
        @Schema(description = "Identifiant du secteur", example = "1")
        private Long sectorId;
        @Schema(description = "Nom du secteur", example = "Informatique")
        private String sectorName;


    }
}
