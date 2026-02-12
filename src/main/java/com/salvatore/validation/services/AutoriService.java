package com.salvatore.validation.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.salvatore.validation.entities.Autore;
import com.salvatore.validation.exceptions.BadRequestException;
import com.salvatore.validation.exceptions.NotFoundException;
import com.salvatore.validation.payloads.AutoreDTO;
import com.salvatore.validation.payloads.NewAutorePayload;
import com.salvatore.validation.repositories.AutoriRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class AutoriService {

    private final AutoriRepository autoriRepository;
    private final Cloudinary cloudinaryUploader;
    @Autowired
    public AutoriService(AutoriRepository autoriRepository, Cloudinary cloudinaryUploader) {
        this.autoriRepository = autoriRepository;
        this.cloudinaryUploader = cloudinaryUploader;
    }

    public Page<Autore> findAll(int page, int size, String orderBy, String sortCriteria){
        if(size > 100 || size < 0) size = 10;
        if(page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size,
                sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.autoriRepository.findAll(pageable);
    }

    public Autore saveAutore(AutoreDTO payload) {
        //1. verifico per email
        this.autoriRepository.findByEmail(payload.email()).ifPresent(autore -> {
            throw new BadRequestException("l'email " + autore.getEmail() + " è già in uso");
        });

        //2. aggiungo campi
        Autore newAutore = new Autore(payload.nome(), payload.cognome(), payload.email(), payload.password(), payload.dataDiNascita());
        newAutore.setAvatar("https://ui-avatars.com/api?name=" + payload.nome() + "+" + payload.cognome());

        //3. salvo
        Autore savedAutore = this.autoriRepository.save(newAutore);

        //4. log
        log.info("l'utente con id " + savedAutore.getId() + " è stato salvato correttametne!");
        // torno l'utente salvato
        return savedAutore;
    }

    public Autore findById(long autoreId){
        return this.autoriRepository.findById(autoreId).orElseThrow(() -> new NotFoundException(autoreId));
    }

    public Autore findByIdAndUpdate(long autoreId, NewAutorePayload payload) {
        Autore found = this.findById(autoreId); // Riutilizzo il metodo sopra
        found.setNome(payload.getNome());
        found.setCognome(payload.getCognome());
        found.setEmail(payload.getEmail());
        found.setPassword(payload.getPassword());
        found.setDataDiNascita(payload.getDataDiNascita());
        found.setAvatar("https://ui-avatars.com/api/?name=" + payload.getNome() + "+" + payload.getCognome());
        return autoriRepository.save(found);
    }


    public void findByAndDelete(long autoreId) {
        Autore found = this.findById(autoreId);
        this.autoriRepository.delete(found);
    }

    public String uploadAvatar(long autoreId, MultipartFile file) {
        try {
            Map result = cloudinaryUploader.uploader().upload(file.getBytes(),
                    ObjectUtils.emptyMap());

            String imageUrl = (String) result.get("secure_url");
            return imageUrl;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        }
}
