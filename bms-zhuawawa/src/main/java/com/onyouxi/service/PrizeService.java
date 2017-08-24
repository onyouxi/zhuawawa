package com.onyouxi.service;

import com.onyouxi.model.dbModel.PrizeModel;
import com.onyouxi.repository.manager.PrizeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by administrator on 2017/8/23.
 */
@Slf4j
@Service
public class PrizeService {

    @Autowired
    private PrizeRepository prizeRepository;

    public PrizeModel save(PrizeModel prizeModel){
        return prizeRepository.insert(prizeModel);
    }

    public void update(PrizeModel prizeModel){
        prizeRepository.save(prizeModel);
    }

    public PrizeModel findById(String id){
        return prizeRepository.findOne(id);
    }

    public Page<PrizeModel> findAll(int pageNum, int pageSize){
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(pageNum, pageSize, sort);
        return prizeRepository.findAll(pageRequest);
    }

    public void del(String id){
        prizeRepository.delete(id);
    }


    public List<PrizeModel> findByIds(List<String> idList){
        return prizeRepository.findByIdIn(idList);
    }


}
