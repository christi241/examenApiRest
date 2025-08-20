package com.isi.ExamenMricoService.mappers;

import com.isi.ExamenMricoService.dto.SchoolClassDto;
import com.isi.ExamenMricoService.entities.SchoolClass;
import com.isi.ExamenMricoService.entities.Sector;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper MapStruct pour la conversion entre les entités SchoolClass et leurs DTOs.
 */
@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SchoolClassMapper {

    /**
     * Convertit une entité SchoolClass en DTO Response.
     *
     * @param schoolClass l'entité à convertir
     * @return le DTO Response correspondant, ou null si l'entité est null
     */
    @Mapping(source = "sector.id", target = "sectorId")
    @Mapping(source = "sector.name", target = "sectorName")
    SchoolClassDto.Response toResponse(SchoolClass schoolClass);

    /**
     * Convertit une liste d'entités SchoolClass en liste de DTOs Response.
     *
     * @param schoolClasses la liste d'entités à convertir
     * @return la liste de DTOs Response correspondante
     */
    List<SchoolClassDto.Response> toResponseList(List<SchoolClass> schoolClasses);

    /**
     * Convertit un DTO CreateRequest en entité SchoolClass.
     * Note: Le secteur n'est pas mappé automatiquement et doit être défini séparément.
     *
     * @param request le DTO de création
     * @return l'entité SchoolClass correspondante, ou null si le DTO est null
     */
    @Mapping(target = "sector", ignore = true)
    @Mapping(target = "id", ignore = true)
    SchoolClass toEntity(SchoolClassDto.CreateRequest request);

    /**
     * Met à jour une entité SchoolClass existante avec les données d'un DTO UpdateRequest.
     * Note: Le secteur et l'ID ne sont pas mis à jour par cette méthode.
     *
     * @param request le DTO contenant les nouvelles données
     * @param schoolClass l'entité existante à mettre à jour (passée en paramètre de sortie)
     */
    @Mapping(target = "sector", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntity(SchoolClassDto.UpdateRequest request, @MappingTarget SchoolClass schoolClass);
}
