package com.wellch4n.service.util;

import lombok.Data;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/11 11:39
 * 下周我就努力工作
 */

@Data
public class Result {
    private Integer code;

    private Object data;

    private String message;

    private boolean success;

}
