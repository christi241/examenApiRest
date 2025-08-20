package com.isi.ExamenMricoService.mappers;

import com.isi.ExamenMricoService.dto.SectorDto;
import com.isi.ExamenMricoService.entities.Sector;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper MapStruct pour la conversion entre les entités Sector et leurs DTOs.
 */
@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SectorMapper {

    /**
     * Convertit une entité Sector en DTO Response.
     *
     * @param sector l'entité à convertir
     * @return le DTO Response correspondant, ou null si l'entité est null
     */
    SectorDto.Response toResponse(Sector sector);

    /**
     * Convertit une liste d'entités Sector en liste de DTOs Response.
     *
     * @param sectors la liste d'entités à convertir
     * @return la liste de DTOs Response correspondante
     */
    List<SectorDto.Response> toResponseList(List<Sector> sectors);

    /**
     * Convertit un DTO CreateRequest en entité Sector.
     *
     * @param request le DTO de création
     * @return l'entité Sector correspondante, ou null si le DTO est null
     */
    Sector toEntity(SectorDto.CreateRequest request);

    /**
     * Met à jour une entité Sector existante avec les données d'un DTO UpdateRequest.
     *
     * @param request le DTO contenant les nouvelles données
     * @param sector l'entité existante à mettre à jour (passée en paramètre de sortie)
     */
    void updateEntity(SectorDto.UpdateRequest request, @MappingTarget Sector sector);
}
