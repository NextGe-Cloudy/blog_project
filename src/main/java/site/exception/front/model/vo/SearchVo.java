package site.exception.front.model.vo;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import site.exception.front.model.Article;
import site.exception.front.model.Tag;

public class SearchVo implements Serializable {
	
	private static final long serialVersionUID = -3434963634918104112L;

	/**
	 * 检索关键字
	 */
	private String q;

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}
}