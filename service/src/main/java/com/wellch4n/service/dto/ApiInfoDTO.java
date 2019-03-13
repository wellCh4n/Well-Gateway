package com.wellch4n.service.dto;

import lombok.Data;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/09 04:30
 * 下周我就努力工作
 */

@Data
public class ApiInfoDTO {
    private Long id;

    private String path;

    private String target;

    private Long allowCount;
}
