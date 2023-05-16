package com.a05.simaya.ziswaf.service;

import antlr.StringUtils;
import com.a05.simaya.ziswaf.model.JenisZiswafEnum;
import com.a05.simaya.ziswaf.model.ZiswafModel;
import com.a05.simaya.ziswaf.payload.ZiswafDTO;
import com.a05.simaya.ziswaf.repository.BarChartData;
import com.a05.simaya.ziswaf.repository.LineChartData;
import com.a05.simaya.ziswaf.repository.ZiswafDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.chrono.ChronoLocalDate;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class ZiswafServiceImpl implements ZiswafService {

    @Autowired
    private ZiswafDb ziswafDb;

    @Override
    public ZiswafModel tambahDonasiZizwaf(ZiswafDTO zizwafDTO) {
        ZiswafModel usahaModel = setZiswafModel(zizwafDTO, new ZiswafModel());
        ziswafDb.save(usahaModel);

        return usahaModel;
    }

    @Override
    public List<ZiswafModel> getListPemasukan() {
        return ziswafDb.findAll();
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
        List<ZiswafModel> listZiswaf = ziswafDb.findAll();
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
        List<ZiswafModel> listZiswaf = ziswafDb.findAll();
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
        List<ZiswafModel> listZiswaf = ziswafDb.findAll();
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

    @Override
    public Object getBarChartData() {
        Map <String, List<?>> result = new HashMap<>();
        Map <JenisZiswafEnum, Integer> indexZiswaf = new HashMap<>();
        List<Long> jumlahZiswaf = new ArrayList<>();
        List<String> labelZiswaf = new ArrayList<>();
        int index = 0;

        for (JenisZiswafEnum ziswaf : JenisZiswafEnum.values()) {
            jumlahZiswaf.add(0L);
            labelZiswaf.add(ziswaf.name().replace("_", " "));
            indexZiswaf.put(ziswaf, index);
            index++;
        }

        List<BarChartData> barChartData = ziswafDb.getBarChartData();
        for (BarChartData data : barChartData) {
            jumlahZiswaf.set(indexZiswaf.get(data.getJenis()), data.getJumlahData());
        }

        result.put("label", labelZiswaf);
        result.put("jumlah", jumlahZiswaf);

        return result;
    }

    @Override
    public Object getLineChartDataDaily() {
        Map <String, Object> result = new HashMap<>();

        LocalDate now = LocalDate.now();
        LocalDate start = LocalDate.of(now.getYear(), now.getMonthValue(), 1);
        LocalDate end = start.plusMonths(1L).minusDays(1L);

        Long[] jumlahZiswaf = new Long[end.getDayOfMonth()];
        LocalDate[] labelZiswaf = new LocalDate[end.getDayOfMonth()];

        List<LineChartData> lineChartData = ziswafDb.getLineChartData(start, end);

        for (int i = 0; i<labelZiswaf.length; i++) {
            labelZiswaf[i] = start.plusDays(i);
            jumlahZiswaf[i] = 0L;
        }

        for (LineChartData data: lineChartData) {
            jumlahZiswaf[data.getTanggal().getDayOfMonth()-1] = data.getJumlah();
        }

        result.put("label", labelZiswaf);
        result.put("jumlah", jumlahZiswaf);

        return result;
    }

    @Override
    public Object getLineChartDataMonthly() {
        Map <String, Object> result = new HashMap<>();

        LocalDate now = LocalDate.now();
        LocalDate start = LocalDate.of(now.getYear(), 1, 1);
        LocalDate end = start.plusYears(1L).minusDays(1L);

        Long[] jumlahZiswaf = new Long[12];
        String[] labelZiswaf = new String[12];

        List<LineChartData> lineChartData = ziswafDb.getLineChartData(start, end);

        for (int i = 0; i<labelZiswaf.length; i++) {
            Locale locale = new Locale("id", "ID");
            String month = start.plusMonths(i).getMonth().getDisplayName(TextStyle.FULL, locale);
            String label = String.format("%s %d", month, start.getYear());
            labelZiswaf[i] = label;
            jumlahZiswaf[i] = 0L;
        }

        for (LineChartData data: lineChartData) {
            jumlahZiswaf[data.getTanggal().getMonthValue()-1] = data.getJumlah();
        }

        result.put("label", labelZiswaf);
        result.put("jumlah", jumlahZiswaf);

        return result;
    }
}
