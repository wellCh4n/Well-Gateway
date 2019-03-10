package com.wellch4n.service.repository;

import com.wellch4n.service.domain.ApiPO;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/10 18:37
 * 下周我就努力工作
 */

@Repository
public interface ApiRepository extends JpaRepository<ApiPO, Long> {
    ApiPO findByPath(String path);
}
