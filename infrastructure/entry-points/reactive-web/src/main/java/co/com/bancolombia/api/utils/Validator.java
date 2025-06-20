package co.com.bancolombia.api.utils;

import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import io.micrometer.common.util.StringUtils;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Validator {
    public static void validateRequired(Object value, BusinessErrorMessage businessErrorMessage){
        if(value==null|| StringUtils.isEmpty(value.toString())){
            throw new BusinessException(businessErrorMessage);
        }
    }
}
