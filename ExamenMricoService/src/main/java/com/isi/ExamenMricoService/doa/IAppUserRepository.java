package com.isi.ExamenMricoService.doa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.isi.ExamenMricoService.entities.AppUserEntity;

public interface IAppUserRepository extends JpaRepository<AppUserEntity, Integer> {
    AppUserEntity findByEmail(String email);
}
