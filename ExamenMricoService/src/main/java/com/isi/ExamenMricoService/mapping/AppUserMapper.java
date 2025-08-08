package com.isi.ExamenMricoService.mapping;

import org.mapstruct.Mapper;
import com.isi.ExamenMricoService.dto.AppUser;
import com.isi.ExamenMricoService.entities.AppUserEntity;


public interface AppUserMapper {
    AppUser toAppUser(AppUserEntity appUserEntity);
    AppUserEntity fromAppUser(AppUser appUser);
}
