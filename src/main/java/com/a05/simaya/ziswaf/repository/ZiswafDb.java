package com.a05.simaya.ziswaf.repository;

import com.a05.simaya.ziswaf.model.ZiswafModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ZiswafDb extends JpaRepository<ZiswafModel, String> {

    @Query(value = "SELECT jenisZiswaf as jenis, sum(jumlah) as jumlahData FROM ZiswafModel group by jenis")
    List<BarChartData> getBarChartData();

    @Query(value = "SELECT donatedDate as tanggal, sum(jumlah) as jumlah FROM ZiswafModel WHERE donatedDate between :start and :end group by tanggal")
    List<LineChartData> getLineChartData(LocalDate start, LocalDate end);
}

