package com.deni.app.module.user;

import com.deni.app.common.constants.Messages;
import com.deni.app.common.service.ResponseService;
import com.deni.app.common.service.ResponseServiceHandler;
import com.deni.app.common.validator.Validator;
import com.deni.app.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements CrudService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserValidator userValidator;

    // for unitest (mocking)
    public UserServiceImpl(UserValidator userValidator, UserRepo userRepo) {
        this.userValidator = userValidator;
        this.userRepo = userRepo;
    }

    @Override
    public ResponseService save(Object requestDTO) {
        UserDTO dto = (UserDTO) requestDTO;
        Validator validator = userValidator.requestValidator(dto);
        if (validator.isSuccess()) {
            validator = userValidator.duplicateValidator(dto.getUsername());
            if (validator.isSuccess()) {

                User entity = User.builder()
                        .username(dto.getUsername())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .permissions(dto.getPermissions())
                        .roles(dto.getRoles())
                        .active(1)
                        .blocked(0)
                        .build();

                User save = userRepo.save(entity);
                return ResponseServiceHandler.createResponse(
                        Messages.MSG_SAVE_SUCCESS,
                        new UserDTO().of(save),
                        HttpStatus.OK);


            } else {
                return ResponseServiceHandler.createResponse(
                        validator.getMessage(),
                        requestDTO,
                        HttpStatus.BAD_REQUEST);
            }
        } else {
            return ResponseServiceHandler.createResponse(
                    validator.getMessage(),
                    requestDTO,
                    HttpStatus.BAD_REQUEST);
        }


    }

    @Override
    public ResponseService update(Long id, Object requestDTO) {
        UserDTO dto = (UserDTO) requestDTO;
        Validator validator = userValidator.requestValidator(dto);
        if (validator.isSuccess()) {
            Optional<User> optional = userRepo.findById(id);
            if (optional.isPresent()) {
                User update = (User) optional.get();
                update.setPermissions(dto.getPermissions());
                update.setRoles(dto.getRoles());
                update.setBlocked(dto.getBlocked());
                update.setActive(dto.getActive());
                update = userRepo.save(update);

                return ResponseServiceHandler.createResponse(
                        Messages.MSG_UPDATE_SUCCESS,
                        new UserDTO().of(update),
                        HttpStatus.OK);


            } else {
                return ResponseServiceHandler.createResponse(
                        Messages.MSG_DATA_NOT_FOUND,
                        requestDTO,
                        HttpStatus.NOT_FOUND);
            }
        } else {
            return ResponseServiceHandler.createResponse(
                    validator.getMessage(),
                    requestDTO,
                    HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseService delete(Long id) {
        Optional<User> optional = userRepo.findById(id);
        if (optional.isPresent()) {
            userRepo.delete(optional.get());

            return ResponseServiceHandler.createResponse(
                    Messages.MSG_DELETE_SUCCESS,
                    optional.get(),
                    HttpStatus.OK);
        } else {
            return ResponseServiceHandler.createResponse(
                    Messages.MSG_DATA_NOT_FOUND,
                    "",
                    HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public ResponseService getAll() {
        List<User> list = userRepo.findAll();
        if (!list.isEmpty()) {
            return ResponseServiceHandler.createResponse(
                    Messages.MSG_DATA_FOUND,
                    new UserDTO().listOf(list),
                    HttpStatus.OK);
        } else {
            return ResponseServiceHandler.createResponse(
                    Messages.MSG_DATA_NOT_FOUND,
                    new UserDTO().listOf(list),
                    HttpStatus.NOT_FOUND);

        }
    }


    @Override
    public ResponseService findById(Long id) {
        Optional<User> optional = userRepo.findById(id);
        if (optional.isPresent()) {
            return ResponseServiceHandler.createResponse(
                    Messages.MSG_DATA_FOUND,
                    new UserDTO().of(optional.get()),
                    HttpStatus.OK);
        } else {
            return ResponseServiceHandler.createResponse(
                    Messages.MSG_DATA_NOT_FOUND,
                    "",
                    HttpStatus.NOT_FOUND);
        }
    }
}
