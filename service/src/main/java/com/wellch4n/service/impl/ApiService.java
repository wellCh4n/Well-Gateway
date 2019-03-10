package com.wellch4n.service.impl;

import com.wellch4n.service.domain.ApiPO;
import com.wellch4n.service.dto.ApiInfoDTO;
import com.wellch4n.service.repository.ApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/09 04:29
 * 下周我就努力工作
 */

@Service
public class ApiService {

    @Autowired
    private ApiRepository apiRepository;

    public ApiInfoDTO findByPath(String path) {
        ApiPO apiPO = apiRepository.findByPath(path);
        ApiInfoDTO apiInfoDTO = new ApiInfoDTO();
        apiInfoDTO.setId(apiPO.getId());
        apiInfoDTO.setPath(apiPO.getPath());
        apiInfoDTO.setTarget(apiPO.getTarget());
        return apiInfoDTO;
    }
}
