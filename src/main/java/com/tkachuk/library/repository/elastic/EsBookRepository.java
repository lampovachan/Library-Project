package com.tkachuk.library.repository.elastic;

import com.tkachuk.library.model.elastic.EsBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


public interface EsBookRepository extends ElasticsearchRepository<EsBook,String> {
    List<EsBook> findByAuthor(String author);

    List<EsBook> findByTitle(String title);

    List<EsBook> findByDescription(String description);

    List<EsBook> findByAuthorTitleDescription(String author, String title, String description);
}