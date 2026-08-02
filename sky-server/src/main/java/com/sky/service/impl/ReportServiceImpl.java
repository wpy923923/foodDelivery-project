package com.sky.service.impl;

import com.sky.mapper.OrderMapper;
import com.sky.entity.Orders;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public TurnoverReportVO getTurnover(String begin, String end) {
        List<LocalDate> dataList = new ArrayList<>();
        while (!begin.equals(end)){
            dataList.add(LocalDate.parse(begin));
            begin = LocalDate.parse(begin).plusDays(1).toString();
        }
        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date : dataList){
            LocalDateTime startTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map<String, Object> map = new HashMap<>();
            map.put("begin", startTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED );
            Double turnover = orderMapper.sumTurnover(map);
            turnover = turnover == null ? 0.0 : turnover;
            turnoverList.add(turnover);
        }
        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dataList,","))
                .turnoverList(StringUtils.join(turnoverList,","))
                .build();
    }
}
