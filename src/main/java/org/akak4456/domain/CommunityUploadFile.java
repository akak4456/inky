package org.akak4456.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="tbl_community_uploadfile")
@EqualsAndHashCode(of="fno")
public class CommunityUploadFile extends UploadFile {

}
