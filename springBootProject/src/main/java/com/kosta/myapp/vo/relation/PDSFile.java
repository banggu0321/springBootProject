package com.kosta.myapp.vo.relation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_pdsfiles")
@EqualsAndHashCode(of = "fno")
public class PDSFile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long fno;
	private String pdsfilename;
}
