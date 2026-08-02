package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ReportService {
    TurnoverReportVO getTurnover(String begin, String end);

    UserReportVO getUserStatistics(String begin, String end);

    OrderReportVO getOrdersStatistics(String begin, String end);

    SalesTop10ReportVO getTop10(LocalDate begin, LocalDate end);
}
