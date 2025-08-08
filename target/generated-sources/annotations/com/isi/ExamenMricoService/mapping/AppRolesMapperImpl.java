package com.isi.ExamenMricoService.mapping;

import com.isi.ExamenMricoService.dto.AppRoles;
import com.isi.ExamenMricoService.entities.AppRolesEntity;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-08T03:13:06+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
public class AppRolesMapperImpl implements AppRolesMapper {

    @Override
    public AppRoles toAppRoles(AppRolesEntity appRolesEntity) {
        if ( appRolesEntity == null ) {
            return null;
        }

        AppRoles appRoles = new AppRoles();

        return appRoles;
    }

    @Override
    public AppRolesEntity fromAppRoles(AppRoles appRoles) {
        if ( appRoles == null ) {
            return null;
        }

        AppRolesEntity appRolesEntity = new AppRolesEntity();

        return appRolesEntity;
    }
}
