package com.isi.ExamenMricoService.mappers;

import com.isi.ExamenMricoService.dto.SchoolClassDto;
import com.isi.ExamenMricoService.entities.SchoolClass;
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
public class SchoolClassMapperImpl implements SchoolClassMapper {

    @Override
    public SchoolClassDto.Response toResponse(SchoolClass schoolClass) {
        if ( schoolClass == null ) {
            return null;
        }

        SchoolClassDto.Response.ResponseBuilder response = SchoolClassDto.Response.builder();

        response.sectorId( schoolClassSectorId( schoolClass ) );
        response.sectorName( schoolClassSectorName( schoolClass ) );
        response.id( schoolClass.getId() );
        response.className( schoolClass.getClassName() );
        response.description( schoolClass.getDescription() );

        return response.build();
    }

    @Override
    public List<SchoolClassDto.Response> toResponseList(List<SchoolClass> schoolClasses) {
        if ( schoolClasses == null ) {
            return null;
        }

        List<SchoolClassDto.Response> list = new ArrayList<SchoolClassDto.Response>( schoolClasses.size() );
        for ( SchoolClass schoolClass : schoolClasses ) {
            list.add( toResponse( schoolClass ) );
        }

        return list;
    }

    @Override
    public SchoolClass toEntity(SchoolClassDto.CreateRequest request) {
        if ( request == null ) {
            return null;
        }

        SchoolClass.SchoolClassBuilder schoolClass = SchoolClass.builder();

        schoolClass.className( request.getClassName() );
        schoolClass.description( request.getDescription() );

        return schoolClass.build();
    }

    @Override
    public void updateEntity(SchoolClassDto.UpdateRequest request, SchoolClass schoolClass) {
        if ( request == null ) {
            return;
        }

        schoolClass.setClassName( request.getClassName() );
        schoolClass.setDescription( request.getDescription() );
    }

    private Long schoolClassSectorId(SchoolClass schoolClass) {
        if ( schoolClass == null ) {
            return null;
        }
        Sector sector = schoolClass.getSector();
        if ( sector == null ) {
            return null;
        }
        Long id = sector.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String schoolClassSectorName(SchoolClass schoolClass) {
        if ( schoolClass == null ) {
            return null;
        }
        Sector sector = schoolClass.getSector();
        if ( sector == null ) {
            return null;
        }
        String name = sector.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
