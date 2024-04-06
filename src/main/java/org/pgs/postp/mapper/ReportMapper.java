package org.pgs.postp.mapper;

import org.pgs.postp.dto.ReportDTO;

import org.pgs.postp.model.ReportModel;

import org.springframework.stereotype.Component;

@Component
public class ReportMapper {


    public ReportDTO toDTO(ReportModel model) {
        ReportDTO dto = new ReportDTO();

        return dto;
    }

    public ReportModel toEntity(ReportDTO reportDTO) {

        ReportModel reportModel = new ReportModel();

        return reportModel;
    }
}
