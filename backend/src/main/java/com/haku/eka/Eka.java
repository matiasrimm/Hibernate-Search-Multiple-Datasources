package com.haku.eka;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Indexed
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Eka {

	@Id
	private long id;
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String info;
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String xinfo;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getXinfo() {
		return xinfo;
	}

	public void setXinfo(String xinfo) {
		this.xinfo = xinfo;
	}

}
	