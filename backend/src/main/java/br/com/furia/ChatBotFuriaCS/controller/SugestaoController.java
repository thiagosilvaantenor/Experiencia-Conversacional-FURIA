package br.com.furia.ChatBotFuriaCS.controller;

import br.com.furia.ChatBotFuriaCS.model.sugestao.Sugestao;

import br.com.furia.ChatBotFuriaCS.service.SugestaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sugestoes")
@CrossOrigin(origins = "*")
public class SugestaoController {
    //Spring vai instanciar a service
    @Autowired
    private SugestaoService service;

    //Mapeamento get em /sugestões para listar todas as sugestões salvas
    @GetMapping
    public ResponseEntity<List<Sugestao>> listarTodas() {
        return ResponseEntity.ok(service.buscaTodas());
    }

    //Mapeamento get em /sugestoes/id para listar uma sugestão especifica
    @GetMapping("/{id}")
    public ResponseEntity<Sugestao> buscarSugestao(@PathVariable Long id){
        Optional<Sugestao> encontrada = service.buscaPorId(id);
        if (encontrada.isPresent()){
            return ResponseEntity.ok(encontrada.get());
        }
        return ResponseEntity.notFound().build();
    }



}
