package org.akak4456.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class Recommend {
	@EmbeddedId
	private RecommendId id;
}
