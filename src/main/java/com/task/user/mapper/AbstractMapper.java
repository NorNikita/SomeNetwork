package com.task.user.mapper;

import com.task.user.dto.use.modelmapper.AbstractDTO;
import com.task.user.entities.AbstractEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


public abstract class AbstractMapper<E extends AbstractEntity, D extends AbstractDTO> extends Mapper<E, D> {

    @Autowired
    private ModelMapper modelMapper;

    private Class<E> entityClass;
    private Class<D> dtoClass;

    AbstractMapper(Class<E> entityClass, Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Override
    public E toEntity(D dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, entityClass);
    }

    @Override
    public D toDto(E entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, dtoClass);
    }

    Converter<E, D> toDtoConverter() {
        return converter -> {
            E entity = converter.getSource();
            D dto = converter.getDestination();
            mapSpecificField(entity, dto);
            return converter.getDestination();
        };
    }

    Converter<D, E> toEntityConverter() {
        return converter -> {
            D dto = converter.getSource();
            E entity = converter.getDestination();
            mapSpecificField(dto, entity);
            return converter.getDestination();
        };
    }

    abstract void mapSpecificField(E entity, D dto);
    abstract void mapSpecificField(D dto, E entity);
}
