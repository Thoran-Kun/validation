package com.salvatore.validation.services;

import com.salvatore.validation.entities.Autore;
import com.salvatore.validation.entities.Blog;
import com.salvatore.validation.exceptions.NotFoundException;
import com.salvatore.validation.payloads.NewBlogPayload;
import com.salvatore.validation.repositories.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BlogsService {

    private final BlogRepository blogRepository;
    private final AutoriService autoriService;

    @Autowired
    public BlogsService(BlogRepository blogRepository, AutoriService autoriService) {
        this.blogRepository = blogRepository;
        this.autoriService = autoriService;
    }

    public Page<Blog> findAll(int page, int size, String orderBy) {
        if(size > 100 || size < 0) size = 10;
        if(page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy).ascending());
        return this.blogRepository.findAll(pageable);
    }

    public Blog saveBlog(NewBlogPayload payload) {
        Autore autore = autoriService.findById(payload.getAutoreId());

        //2. creo ora il nuovo blog dopo che ho controllato se esiste l'autore tramite il suo id
        Blog newBlog = new Blog();
        newBlog.setCategoria(payload.getCategoria());
        newBlog.setTitolo(payload.getTitolo());
        newBlog.setContenuto(payload.getContenuto());
        newBlog.setTempoDiLettura(payload.getTempoDiLettura());
        newBlog.setAutore(autore); // Fondamentale per la relazione ManyToOne!

        // 3. Salviamo nel DB
        Blog savedBlog = this.blogRepository.save(newBlog);

        log.info("Il Blog con id " + savedBlog.getId() + " è stato salvato correttamente");
        return savedBlog;

    }

    public Blog findById(long blogId) {
        return this.blogRepository.findById(blogId)
                .orElseThrow(() -> new NotFoundException(blogId));
    }

    public Blog findByIdAndUpdate(long blogId, NewBlogPayload payload) {
        Blog found = this.findById(blogId);

        found.setCategoria(payload.getCategoria());
        found.setTitolo(payload.getTitolo());
        found.setContenuto(payload.getContenuto());
        found.setTempoDiLettura(payload.getTempoDiLettura());

        // Se nel payload cambia anche l'autore, aggiorniamo anche quello
        Autore nuovoAutore = autoriService.findById(payload.getAutoreId());
        found.setAutore(nuovoAutore);

        return this.blogRepository.save(found);
    }

    public void findByIdAndDelete(long blogId) {
        Blog found = this.findById(blogId);
        this.blogRepository.delete(found);
    }
}
