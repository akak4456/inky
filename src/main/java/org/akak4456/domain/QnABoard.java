package org.akak4456.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude= "replies")
@Entity
@Table(name="tbl_qna_board")
@EqualsAndHashCode(of="bno")
public class QnABoard extends Board<QnAReply, QnAUploadFile> {

}
