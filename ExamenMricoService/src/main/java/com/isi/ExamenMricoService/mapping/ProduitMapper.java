package com.isi.ExamenMricoService.mapping;

import org.mapstruct.Mapper;
import com.isi.ExamenMricoService.dto.Produit;
import com.isi.ExamenMricoService.entities.ProduitEntity;

@Mapper
public interface ProduitMapper {
    Produit toProduit(ProduitEntity produitEntity);
    ProduitEntity fromProduit(Produit produit);
}
