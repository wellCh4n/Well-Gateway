package com.wellch4n.service.impl;

import com.wellch4n.service.dto.ApiAddParam;
import com.wellch4n.service.po.ApiPO;
import com.wellch4n.service.dto.ApiInfoDTO;
import com.wellch4n.service.repository.ApiRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        BeanUtils.copyProperties(apiPO, apiInfoDTO);
        return apiInfoDTO;
    }

    public List<ApiInfoDTO> findAll() {
        return apiRepository.findAll()
                .stream()
                .map(po -> {
                    ApiInfoDTO apiInfoDTO = new ApiInfoDTO();
                    BeanUtils.copyProperties(po, apiInfoDTO);
                    return apiInfoDTO;
                })
                .collect(Collectors.toList());
    }

    public long countApi() {
        return apiRepository.count();
    }

    public void addApi(ApiAddParam apiAddParam) {
        ApiPO apiPO = new ApiPO();
        BeanUtils.copyProperties(apiAddParam, apiPO);
        apiRepository.save(apiPO);
    }
}
