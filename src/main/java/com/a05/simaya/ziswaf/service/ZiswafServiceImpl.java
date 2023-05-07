package com.a05.simaya.ziswaf.service;

import com.a05.simaya.ziswaf.model.JenisZiswafEnum;
import com.a05.simaya.ziswaf.model.ZiswafModel;
import com.a05.simaya.ziswaf.payload.ZiswafDTO;
import com.a05.simaya.ziswaf.repository.ZiswafDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ZiswafServiceImpl implements ZiswafService {

    @Autowired
    private ZiswafDb zizwafDb;

    @Override
    public ZiswafModel tambahDonasiZizwaf(ZiswafDTO zizwafDTO) {
        ZiswafModel usahaModel = setZiswafModel(zizwafDTO, new ZiswafModel());
        zizwafDb.save(usahaModel);

        return usahaModel;
    }

    private ZiswafModel setZiswafModel(ZiswafDTO zizwafDTO, ZiswafModel ziswafModel) {
        ziswafModel.setDonatur(zizwafDTO.getDonatur());
        ziswafModel.setJumlah(zizwafDTO.getJumlah());
        ziswafModel.setNoTelp(zizwafDTO.getNoTelp());
        ziswafModel.setJenisZiswaf(JenisZiswafEnum.valueOf(zizwafDTO.getJenisZiswaf()));
        ziswafModel.setCreatedDate(LocalDateTime.now());
        ziswafModel.setDonatedDate(zizwafDTO.getDonatedDate());

        return ziswafModel;
    }
}
