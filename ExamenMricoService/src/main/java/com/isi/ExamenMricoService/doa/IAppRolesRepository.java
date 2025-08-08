package com.isi.ExamenMricoService.doa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.isi.ExamenMricoService.entities.AppRolesEntity;

public interface IAppRolesRepository extends JpaRepository<AppRolesEntity, Integer> {
}
