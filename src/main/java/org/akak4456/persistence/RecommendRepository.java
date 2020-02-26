package org.akak4456.persistence;

import org.akak4456.domain.CommunityRecommend;
import org.akak4456.domain.Recommend;
import org.akak4456.domain.RecommendId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
@NoRepositoryBean
public interface RecommendRepository <T extends Recommend> extends CrudRepository<T, RecommendId> {

}
