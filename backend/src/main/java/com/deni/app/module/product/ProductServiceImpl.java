package com.deni.app.module.product;

import com.deni.app.common.constants.Messages;
import com.deni.app.common.service.ResponseService;
import com.deni.app.common.service.ResponseServiceHandler;
import com.deni.app.common.validator.Validator;
import com.deni.app.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements CrudService {

    @Autowired
    ProductRepo productRepo;


    @Autowired
    ProductValidator reqValidator;

    // for unitest (mocking)
    public ProductServiceImpl(ProductValidator reqValidator, ProductRepo productRepo) {
        this.reqValidator = reqValidator;
        this.productRepo = productRepo;
    }

    @Override
    public ResponseService save(Object requestDTO) {
        ProductDTO dto = (ProductDTO) requestDTO;
        Validator validator = reqValidator.requestValidator(dto);
        if (validator.isSuccess()) {
            validator = reqValidator.duplicateValidator(dto.getName());
            if (validator.isSuccess()) {

                Product entity = Product.builder()
                        .name(dto.getName())
                        .sellPrice(dto.getSellPrice())
                        .build();

                Product save = productRepo.save(entity);

                return ResponseServiceHandler.createResponse(
                        Messages.MSG_SAVE_SUCCESS,
                        new ProductDTO().of(save),
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
        ProductDTO dto = (ProductDTO) requestDTO;
        Validator validator = reqValidator.requestValidator(dto);
        if (validator.isSuccess()) {
            Optional<Product> optional = productRepo.findById(id);
            if (optional.isPresent()) {
                Product update = (Product) optional.get();
                update.setName(dto.getName());
                update.setSellPrice(dto.getSellPrice());
                update = productRepo.save(update);

                return ResponseServiceHandler.createResponse(
                        Messages.MSG_UPDATE_SUCCESS,
                        new ProductDTO().of(update),
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
        Optional<Product> optional = productRepo.findById(id);
        if (optional.isPresent()) {
            productRepo.delete(optional.get());

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
        List<Product> list = productRepo.findAll();
        if (!list.isEmpty()) {
            return ResponseServiceHandler.createResponse(
                    Messages.MSG_DATA_FOUND,
                    new ProductDTO().listOf(list),
                    HttpStatus.OK);
        } else {
            return ResponseServiceHandler.createResponse(
                    Messages.MSG_DATA_NOT_FOUND,
                    new ProductDTO().listOf(list),
                    HttpStatus.NOT_FOUND);

        }
    }


    @Override
    public ResponseService findById(Long id) {
        Optional<Product> optional = productRepo.findById(id);
        if (optional.isPresent()) {
            return ResponseServiceHandler.createResponse(
                    Messages.MSG_DATA_FOUND,
                    new ProductDTO().of(optional.get()),
                    HttpStatus.OK);
        } else {
            return ResponseServiceHandler.createResponse(
                    Messages.MSG_DATA_NOT_FOUND,
                    "",
                    HttpStatus.NOT_FOUND);
        }
    }
}
