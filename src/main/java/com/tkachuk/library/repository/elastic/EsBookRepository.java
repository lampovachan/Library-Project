package com.tkachuk.library.repository.elastic;

import com.tkachuk.library.model.elastic.EsBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


public interface EsBookRepository extends ElasticsearchRepository<EsBook,String> {
    Page<EsBook> findByAuthor(String author, Pageable pageable);

    List<EsBook> findByTitle(String title);

    Page<EsBook> findByDescription(String description, Pageable pageable);
}