package br.com.cursojava.tudolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

//pega os valores vazios e converte em um array
public class Utils {

    //copiar tudo que for nulo
    public static void copyNonNullPropeties(Object source, Object target){
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));//copia os valores de um objeto para outro
    }


    //pega toda propriedade nula
    public static String[] getNullPropertyNames(Object source){
        final BeanWrapper src = new BeanWrapperImpl(source);

        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for(PropertyDescriptor pd: pds){
            Object srcValue = src.getPropertyValue(pd.getName());
            if(srcValue == null){
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    };
}
