package org.akak4456.domain;

import java.util.List;

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
public class CommunityUploadFile extends UploadFile{

}
