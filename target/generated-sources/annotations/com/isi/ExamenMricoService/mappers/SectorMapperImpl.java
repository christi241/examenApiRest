package com.isi.ExamenMricoService.mappers;

import com.isi.ExamenMricoService.dto.SectorDto;
import com.isi.ExamenMricoService.entities.Sector;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-20T15:57:25+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class SectorMapperImpl implements SectorMapper {

    @Override
    public SectorDto.Response toResponse(Sector sector) {
        if ( sector == null ) {
            return null;
        }

        SectorDto.Response.ResponseBuilder response = SectorDto.Response.builder();

        response.id( sector.getId() );
        response.name( sector.getName() );

        return response.build();
    }

    @Override
    public List<SectorDto.Response> toResponseList(List<Sector> sectors) {
        if ( sectors == null ) {
            return null;
        }

        List<SectorDto.Response> list = new ArrayList<SectorDto.Response>( sectors.size() );
        for ( Sector sector : sectors ) {
            list.add( toResponse( sector ) );
        }

        return list;
    }

    @Override
    public Sector toEntity(SectorDto.CreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Sector.SectorBuilder sector = Sector.builder();

        sector.name( request.getName() );

        return sector.build();
    }

    @Override
    public void updateEntity(SectorDto.UpdateRequest request, Sector sector) {
        if ( request == null ) {
            return;
        }

        sector.setName( request.getName() );
    }
}
