package com.isi.ExamenMricoService.mapping;

import com.isi.ExamenMricoService.dto.Produit;
import com.isi.ExamenMricoService.entities.ProduitEntity;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-08T03:13:05+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
public class ProduitMapperImpl implements ProduitMapper {

    @Override
    public Produit toProduit(ProduitEntity produitEntity) {
        if ( produitEntity == null ) {
            return null;
        }

        Produit produit = new Produit();

        return produit;
    }

    @Override
    public ProduitEntity fromProduit(Produit produit) {
        if ( produit == null ) {
            return null;
        }

        ProduitEntity produitEntity = new ProduitEntity();

        return produitEntity;
    }
}
