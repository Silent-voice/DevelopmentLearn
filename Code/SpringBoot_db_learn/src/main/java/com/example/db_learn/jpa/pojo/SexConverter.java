package com.example.db_learn.jpa.pojo;

import javax.persistence.AttributeConverter;

public class SexConverter implements AttributeConverter<SexEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SexEnum sex) {
        return sex.getId();
    }

    @Override
    public SexEnum convertToEntityAttribute(Integer id) {
        return SexEnum.getEnumByid(id);
    }
}
