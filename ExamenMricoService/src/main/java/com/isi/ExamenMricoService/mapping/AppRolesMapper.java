package com.isi.ExamenMricoService.mapping;

import org.mapstruct.Mapper;
import com.isi.ExamenMricoService.dto.AppRoles;
import com.isi.ExamenMricoService.entities.AppRolesEntity;

@Mapper
public interface AppRolesMapper {
    AppRoles toAppRoles(AppRolesEntity appRolesEntity);
    AppRolesEntity fromAppRoles(AppRoles appRoles);
}
