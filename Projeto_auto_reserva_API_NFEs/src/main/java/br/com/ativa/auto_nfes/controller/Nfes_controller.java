package br.com.ativa.auto_nfes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ativa.auto_nfes.service.Nfes_service;

@RestController
@RequestMapping("/api")
public class Nfes_controller {
    @Autowired
    Nfes_service service;

}
