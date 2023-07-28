package com.deni.app.common.service;

import com.deni.app.common.service.ResponseService;
import org.springframework.stereotype.Service;

@Service
public interface CrudService {


    public ResponseService save(Object requestDTO);


    public ResponseService update(Long id, Object requestDTO);

    public ResponseService delete(Long id);


    public ResponseService getAll();


    public ResponseService findById(Long id);

}
