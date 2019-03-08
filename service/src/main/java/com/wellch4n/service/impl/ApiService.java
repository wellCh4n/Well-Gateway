package com.wellch4n.service.impl;

import com.wellch4n.service.dto.ApiInfoDTO;
import org.springframework.stereotype.Service;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/09 04:29
 * 下周我就努力工作
 */

@Service
public class ApiService {

    public ApiInfoDTO findByPath(String path) {
        ApiInfoDTO apiInfoDTO = new ApiInfoDTO();
        apiInfoDTO.setTarget("www.baidu.com");
        return apiInfoDTO;
    }
}
