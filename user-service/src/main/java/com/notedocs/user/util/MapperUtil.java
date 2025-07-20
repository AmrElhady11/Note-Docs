package com.notedocs.user.util;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
@AllArgsConstructor
@Component
public class MapperUtil {
    private  ModelMapper modelMapper ;



    public  <S, D> D mapEntity(S source, Class<D> destinationClass) {
        return modelMapper.map(source, destinationClass);
    }
    public  <S,D>  Set<D> mapList(Set<S> source, Class<D> destinationClass) {
        return source.stream().map(sourceClass -> mapEntity(sourceClass, destinationClass)).collect(Collectors.toSet());
    }
}
