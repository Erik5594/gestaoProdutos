package com.github.erik5594.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Data;

@Named
@ViewScoped
public @Data class imagensSlide implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> imagens;

	@PostConstruct
	public void init() {
		imagens = new ArrayList<>();
		for (int i = 1; i <= 3; i++) {
			imagens.add("slide" + i + ".jpg");
		}
	}
}
