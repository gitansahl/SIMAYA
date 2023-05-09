package com.a05.simaya.ziswaf.service;

import com.a05.simaya.ziswaf.model.ZiswafModel;
import com.a05.simaya.ziswaf.payload.ZiswafDTO;

import java.util.List;

public interface ZiswafService {
    ZiswafModel tambahDonasiZizwaf(ZiswafDTO zizwafDTO);
    Long getZiswafToday();
    Long getZiswafLastWeek();
    Long getZiswafLastMonth();
}
