package com.deni.app.module.auth;//package com.denpaden.springbootservice.unit.service.validator;

import com.deni.app.common.constants.Messages;
import com.deni.app.common.utils.NullEmptyChecker;
import com.deni.app.common.validator.Validator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginValidator {


    public Validator requestValidator(LoginDTO dto) {
        List<String> message = new ArrayList<>();


        if (NullEmptyChecker.isNullOrEmpty(dto.getUsername())) {
            message.add(String.format(Messages.MSG_FIELD_CANNOT_BE_NULL_OR_EMPTY, "username"));
        }

        if (NullEmptyChecker.isNullOrEmpty(dto.getPassword())) {
            message.add(String.format(Messages.MSG_FIELD_CANNOT_BE_NULL_OR_EMPTY, "password"));
        }


        if (message.isEmpty()) {
            return new Validator().yes();
        } else {
            String result = "";
            for (String str : message) {
                if (result.isEmpty()) {
                    result = str;
                } else {
                    result += ", " + str;
                }
            }
            return new Validator().no(result);
        }
    }


}
