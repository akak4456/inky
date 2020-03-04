package org.akak4456.persistence;

import org.akak4456.domain.QCommunityReply;
import org.akak4456.domain.QReport;
import org.akak4456.domain.Report;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface ReportRepository extends CrudRepository<Report,Long>,QuerydslPredicateExecutor<Report>{
	public default Predicate makePredicate() {
		BooleanBuilder builder = new BooleanBuilder();
		
		QReport report = QReport.report;
		
		builder.and(report.rno.gt(0));
		return builder;
	} 
}
