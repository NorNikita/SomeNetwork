package com.task.user.mapper;

import com.task.user.dto.use.modelmapper.AbstractDTO;
import com.task.user.entities.AbstractEntity;

public abstract class Mapper<E extends AbstractEntity, D extends AbstractDTO> {
    public abstract E toEntity(D dto);

    public abstract D toDto(E entity);
}
