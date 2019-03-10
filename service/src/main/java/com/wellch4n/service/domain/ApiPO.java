package com.wellch4n.service.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/09 04:30
 * 下周我就努力工作
 */

@Data
@Entity
@Table(name = "wl_api")
public class ApiPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path")
    private String path;

    @Column(name = "target")
    private String target;
}
