package org.akak4456.persistence;

import org.akak4456.domain.CommunityRecommend;
import org.akak4456.domain.Recommend;
import org.akak4456.domain.RecommendId;
import org.springframework.data.repository.CrudRepository;

public interface RecommnedRepository <T extends Recommend> extends CrudRepository<T, RecommendId> {

}
