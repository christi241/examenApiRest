package com.isi.ExamenMricoService.doa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.isi.ExamenMricoService.entities.ProduitEntity;

public interface IProduitRepository extends JpaRepository<ProduitEntity, Integer> {
}
