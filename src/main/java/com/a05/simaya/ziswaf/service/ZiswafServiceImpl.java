package com.a05.simaya.ziswaf.service;

import com.a05.simaya.event.repository.EventDb;
import com.a05.simaya.ziswaf.model.JenisZiswafEnum;
import com.a05.simaya.ziswaf.model.ZiswafModel;
import com.a05.simaya.ziswaf.payload.ZiswafDTO;
import com.a05.simaya.ziswaf.repository.ZiswafDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Long getZiswafToday() {
        List<ZiswafModel> listZiswaf = zizwafDb.findAll();
        Long sum = 0L;
        if (listZiswaf.size() != 0) {
            LocalDateTime now = LocalDateTime.now();
            for (ZiswafModel ziswaf : listZiswaf) {
                if (ziswaf.getDonatedDate().isEqual(ChronoLocalDate.from(now))) {
                    sum += ziswaf.getJumlah();
                }
            }
        }
        return sum;
    }

    @Override
    public Long getZiswafLastWeek() {
        List<ZiswafModel> listZiswaf = zizwafDb.findAll();
        Long sum = 0L;
        if (listZiswaf.size() != 0) {
            LocalDateTime now = LocalDateTime.now();
            for (ZiswafModel ziswaf : listZiswaf) {
                if (ziswaf.getDonatedDate().isAfter(ChronoLocalDate.from(now.minusWeeks(1))) &&
                        ziswaf.getDonatedDate().isBefore(ChronoLocalDate.from(now.plusDays(1)))) {
                    sum += ziswaf.getJumlah();
                }
            }
        }
        return sum;
    }

    @Override
    public Long getZiswafLastMonth() {
        List<ZiswafModel> listZiswaf = zizwafDb.findAll();
        Long sum = 0L;
        if (listZiswaf.size() != 0) {
            LocalDateTime now = LocalDateTime.now();
            for (ZiswafModel ziswaf : listZiswaf) {
                if (ziswaf.getDonatedDate().isAfter(ChronoLocalDate.from(now.minusMonths(1))) &&
                        ziswaf.getDonatedDate().isBefore(ChronoLocalDate.from(now.plusDays(1)))) {
                    sum += ziswaf.getJumlah();
                }
            }
        }
        return sum;
    }
}
