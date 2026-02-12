package com.salvatore.validation.controllers;

import com.salvatore.validation.entities.Autore;
import com.salvatore.validation.exceptions.ValidationException;
import com.salvatore.validation.payloads.AutoreDTO;
import com.salvatore.validation.payloads.NewAutorePayload;
import com.salvatore.validation.services.AutoriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/*

1. POST http://localhost:3001/
2. GET http://localhost:3001/
3. GET http://localhost:3001/
4. PUT http://localhost:3001/
5. DELETE http://localhost:3001/

*/

@RestController
@RequestMapping("/autori")
public class AutoriController {
    private final AutoriService autoriService;

    @Autowired
    public AutoriController(AutoriService autoriService) {
        this.autoriService = autoriService;
    }

    @GetMapping
    public Page<Autore> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String orderBy,
            @RequestParam(defaultValue = "asc") String sortCriteria){
        return this.autoriService.findAll(page, size, orderBy, sortCriteria);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Autore createAutore(@RequestBody @Validated AutoreDTO payload,
                               BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        } else {
            return this.autoriService.saveAutore(payload);
        }
    }

    @GetMapping("/{autoreId}")
    public Autore getAutoreById(@PathVariable long autoreId){
        return this.autoriService.findById(autoreId);
    }

    @PutMapping("/{autoreId}")
    public Autore updateAutore(@PathVariable long autoreId,
                               @RequestBody @Validated NewAutorePayload payload,
                               BindingResult validationResult){
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage()).toList());
        }
        return this.autoriService.findByIdAndUpdate(autoreId, payload);
    }

    @DeleteMapping("/{autoreId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getAutoreIdAndDelete(@PathVariable long autoreId){
        this.autoriService.findByAndDelete(autoreId);
    }
}
