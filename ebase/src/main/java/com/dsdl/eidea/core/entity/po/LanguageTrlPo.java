package com.dsdl.eidea.core.entity.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * CoreLanguageTrl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "core_language_trl", catalog = "e_idea")
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class LanguageTrlPo implements java.io.Serializable {

	// Fields
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lang", nullable = false)
	private LanguagePo coreLanguageByLang;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "language_code", nullable = false)
	private LanguagePo coreLanguageByLanguageCode;
	@Column(name = "name", nullable = false, length = 200)
	private String name;


	// Constructors

	/** default constructor */
	public LanguageTrlPo() {
	}

	/** full constructor */
	public LanguageTrlPo(Integer id, LanguagePo coreLanguageByLang,
			LanguagePo coreLanguageByLanguageCode, String name) {
		this.id = id;
		this.coreLanguageByLang = coreLanguageByLang;
		this.coreLanguageByLanguageCode = coreLanguageByLanguageCode;
		this.name = name;
	}

}