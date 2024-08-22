package com.example.shipmentservicelight.ttt.args;

import com.example.shipmentservicelight.ttt.enums.StagePhases;
import com.example.shipmentservicelight.ttt.ex.StageException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HandlerArgumentsValidator {
    public static void validate(List<? extends HandlerArgument> expected, List<? extends HandlerArgument> actual, StagePhases stagePhases){
        if(actual.size() != expected.size())
            throw new StageException("НЕ та длина", stagePhases);

        for(int i = 0;i<expected.size();i++){
            if(!expected.get(i).equals(actual.get(i))){
                throw new StageException(String.format("Ошибка в валидации двух параметров ожидалось %s а полочили %S", expected.get(i), actual.get(i)), stagePhases);
            }
        }
    }
}
