package org.akak4456.service;

import org.akak4456.domain.Report;
import org.akak4456.persistence.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public class ReportServiceImpl implements ReportService {
	@Autowired
	private ReportRepository reportRepo;
	@Override
	public void writeReport(Report report) {
		// TODO Auto-generated method stub
		reportRepo.save(report);
	}
	@Override
	public Page<Report> getListWithPaging(Pageable pageable) {
		// TODO Auto-generated method stub
		return reportRepo.findAll(reportRepo.makePredicate(),pageable);
	}
	@Override
	public void deleteAllReport() {
		// TODO Auto-generated method stub
		reportRepo.deleteAll();
	}

}
