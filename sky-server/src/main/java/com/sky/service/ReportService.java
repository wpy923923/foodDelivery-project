package com.sky.service;

import com.sky.vo.TurnoverReportVO;

public interface ReportService {
    TurnoverReportVO getTurnover(String begin, String end);
}
