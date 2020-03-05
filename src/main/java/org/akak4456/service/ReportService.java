package org.akak4456.service;

import org.akak4456.domain.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportService {
	public void writeReport(Report report);
	public Page<Report> getListWithPaging(Pageable pageable);
	public void deleteAllReport();
}
