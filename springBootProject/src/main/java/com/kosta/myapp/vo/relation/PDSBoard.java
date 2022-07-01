package com.kosta.myapp.vo.relation;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor //argu없는생성자
@AllArgsConstructor //argu모두 생성자
@Builder	//builder().build()로 데이터넣기
@Data
@Entity
@Table(name = "tbl_pdsboard")
@EqualsAndHashCode(of = "pid")
//@ToString(exclude = "files2") //fetch = FetchType.LAZY인 경우 exclude로 칼럼을 제외한다.
public class PDSBoard {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long pid;
	private String pname;
	private String pwriter;

	//cascade : 영속성 전이 PDBBoard가 수정되면 PDSFile변경
	//fetch : EAGER(즉시로딩), LAZY(지연로딩)
	//PDS조회시 자식인 PDSFile가 조인하여 조회
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "pdsno") // PDSFile칼럼에 생성
	private List<PDSFile> files2; //PDBBoard에는 files2칼럼은 존재하지 않는다.
}
