package com.tkachuk.library.repository.elastic;

import com.tkachuk.library.model.elastic.EsBook;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.awt.print.Pageable;

public interface EsBookRepository extends ElasticsearchRepository<EsBook,String> {

    Page<EsBook> findDistinctByTitleContainingOrContentContainingOrAuthorContaining(String title, String description, String author,
                                                                                    Pageable pageable);

    Page<EsBook> findDistinctByTitleContainingOrContentContaining(String title,String content, Pageable pageable);

    void deleteEsBookByArticleId(String id);
}