package com.deni.app.module.product;//package com.denpaden.springbootservice.unit.service.validator;

import com.deni.app.common.constants.Messages;
import com.deni.app.common.validator.Validator;
import com.deni.app.module.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductValidator {

    @Autowired
    ProductRepo repo;

    public Validator duplicateValidator(String name) {
        User entity = repo.findByName(name);
        if (entity != null) {
            return new Validator().no(String.format(Messages.MSG_DATA_ALREADY_EXIST, entity.getUsername()));
        } else {
            return new Validator().yes(Messages.MSG_DATA_IS_AVAILABLE);
        }
    }

    public Validator requestValidator(ProductDTO dto) {
        List<String> message = new ArrayList<>();

        if (dto.getName() == null) {
            message.add(String.format(Messages.MSG_FIELD_CANNOT_BE_NULL, "name"));
        } else {
            if (dto.getName().isEmpty()) {
                message.add(String.format(Messages.MSG_FIELD_CANNOT_BE_EMPTY, "name"));
            }
        }

        if (dto.getSellPrice() == null) {
            message.add(String.format(Messages.MSG_FIELD_CANNOT_BE_NULL, "sellPrice"));
        } else {
            if (dto.getSellPrice() < 0) {
                message.add(String.format(Messages.MSG_FIELD_CANNOT_INPUT_ZERO, "sellPrice"));
            }
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
