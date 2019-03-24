package com.wellch4n.service.dto;

import lombok.Data;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/24 14:22
 * 下周我就努力工作
 */

@Data
public class ApiAddParam {
    private String path;

    private String target;

    private Long allowCount;
}
