package org.akak4456.persistence;

import java.util.List;

import org.akak4456.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, String> {
	public List<Member> findByUemail(String uemail);
}
