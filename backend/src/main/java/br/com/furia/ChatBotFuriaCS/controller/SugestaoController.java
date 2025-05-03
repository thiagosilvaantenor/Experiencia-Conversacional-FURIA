package br.com.furia.ChatBotFuriaCS.controller;

import br.com.furia.ChatBotFuriaCS.model.sugestao.Sugestao;

import br.com.furia.ChatBotFuriaCS.model.sugestao.SugestaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sugestoes")
@CrossOrigin(origins = "*")
public class SugestaoController {
    @Autowired
    private SugestaoService service;

    @GetMapping
    public ResponseEntity<List<Sugestao>> listarTodas() {
        return ResponseEntity.ok(service.buscaTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sugestao> buscarSugestao(@PathVariable Long id){
        Optional<Sugestao> encontrada = service.buscaPorId(id);
        if (encontrada.isPresent()){
            return ResponseEntity.ok(encontrada.get());
        }
        return ResponseEntity.notFound().build();
    }



}
