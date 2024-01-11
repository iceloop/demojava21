package com.example.demojava21.service;


import com.example.demojava21.domain.Article;
import com.example.demojava21.domain.dto.ArticleDTO;
import com.example.demojava21.repository.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * AsyncService
 * <p>
 * 创建人：hrniu
 * 创建日期：2024/1/2
 */
@Service
public class AsyncService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ModelMapper modelMapper;
    /**
     * @param countDownLatch 用于测试
     */
    @Async
    public void doSomething(CountDownLatch countDownLatch) throws InterruptedException {
        Thread.sleep(50);
/*        System.out.println(Thread.currentThread().isVirtual());
        System.out.println(Thread.currentThread().toString());*/
        countDownLatch.countDown();
    }

    public List<ArticleDTO> getarticleslist() {
        List<Article> list = articleRepository.findAll();
        // list转换
        List<ArticleDTO> lastlist = list.stream().map(article -> {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setId(article.getId());
            articleDTO.setTitle(article.getTitle());
            articleDTO.setContent(article.getContent());
            articleDTO.setViewNum(article.getViewNum());
            articleDTO.setCreatedAt(article.getCreatedAt());
            articleDTO.setUpdatedAt(article.getUpdatedAt());
            articleDTO.setDeletedAt(article.getDeletedAt());
            return articleDTO;
        }).collect(Collectors.toList());
        return lastlist;
    }


    public List<ArticleDTO> getarticleslistfactory() {
        List<Article> list = articleRepository.findAll();
        // list转换
        return list.stream()
                .map(ArticleDTO::new)
                .collect(Collectors.toList());
    }

    public List<ArticleDTO> getarticleslistmodelmapper() {
        List<Article> list = articleRepository.findAll();
        return list.stream()
                .map(article -> modelMapper.map(article, ArticleDTO.class))
                .collect(Collectors.toList());
    }

    public List<ArticleDTO> getarticleslistbeanutils() {
        List<Article> list = articleRepository.findAll();
        return list.stream()
                .map(article -> {
                    ArticleDTO articleDTO = new ArticleDTO();
                    BeanUtils.copyProperties(article, articleDTO);
                    return articleDTO;
                })
                .collect(Collectors.toList());
    }


}

