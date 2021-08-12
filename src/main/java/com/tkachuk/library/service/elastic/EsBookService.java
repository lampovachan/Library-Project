package com.tkachuk.library.service.elastic;

import com.tkachuk.library.model.elastic.EsBook;
import com.tkachuk.library.repository.elastic.EsBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EsBookService {

    @Autowired
    private EsBookRepository esBookRepository;
    // public EsBook saveOrUpdate(EsBook esBook) {
//        return esBookRepository.save(esBook);
//    }
//
//    public Page<EsBook> queryBook(String content, org.springframework.data.domain.Pageable pageable) {
//        return esBookRepository.findDistinctByTitleContainingOrContentContaining(content,content, pageable);
//    }
//
//    public Page<EsBook> queryBook(String title, String content, String author, Pageable pageable) {
//        return esBookRepository.findDistinctByTitleContainingOrContentContainingOrAuthorContaining(title,content,author,pageable);
//    }
//
//    public void deleteByBookId(String id) {
//        esBookRepository.deleteEsBookByArticleId(id);
//    }
//
//    public Page<EsBook> findAll(org.springframework.data.domain.Pageable pageable) {
//        return esBookRepository.findAll(pageable);
//    }

//
}