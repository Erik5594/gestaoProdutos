function FormataCPFCNPJ(campo, teclapres) {
	if (campo.value.length < 15) {
		if (campo.value.length < 14) {
			FormataCPF(campo, teclapres);
		} else if (campo.value.length == 14) {
			num = String(campo.value.substr(3, 1));
			// alert("num: "+num)
			if (num != ".") {
				// alert("cnpj");
				FormataCNPJ(campo, teclapres);
			} else {
				// alert("cnpf");
				FormataCPF(campo, teclapres);
			}
		}
	} else {
		FormataCNPJ(campo, teclapres);
	}
}

function FormataCPF(campo, teclapres) {
	var tecla = (window.Event) ? teclapres.which : teclapres.keyCode;
	vr = campo.value;
	if ("0123456789".search(vr.substr(vr.length - 1, 1)) == -1) {
		vr = vr.substr(0, vr.length - 1);
		campo.value = vr;
	} else {
		vr = vr.replace(".", "");
		vr = vr.replace(".", "");
		vr = vr.replace("-", "");
		tam = vr.length + 1;
		if (tecla != 9 && tecla != 8) {
			if (tam > 3 && tam < 7) {
				campo.value = vr.substr(0, 3) + '.'
						+ vr.substr(3);
			}
			if (tam >= 7 && tam < 10) {
				campo.value = vr.substr(0, 3) + '.'
						+ vr.substr(3, 3) + '.' + vr.substr(6);
			}
			if (tam >= 10 && tam <= 11) {
				campo.value = vr.substr(0, 3) + '.'
						+ vr.substr(3, 3) + '.' + vr.substr(6, 3) + '-'
						+ vr.substr(9);
			}
		}
		// usado no onChange para completar se o usuario tiver colado o numero
		if (campo.value.length == 11) {
			var cnpf = campo.value;
			cnpf = cnpf.substr(0, 3) + '.' + cnpf.substr(3, 3) + '.'
					+ cnpf.substr(6, 3) + '-' + cnpf.substr(9);
			campo.value = cnpf;
		}
	}
}

function FormataCNPJ(campo, teclapres) {
	var tecla = (window.Event) ? teclapres.which : teclapres.keyCode;
	vr = campo.value;
	if ("0123456789".search(vr.substr(vr.length - 1, 1)) == -1) {
		vr = vr.substr(0, vr.length - 1);
		campo.value = vr;
	} else {
		vr = vr.replace(".", "");
		vr = vr.replace(".", "");
		vr = vr.replace("/", "");
		vr = vr.replace("-", "");
		tam = vr.length + 1;
		if (tecla != 9 && tecla != 8) {
			if (tam > 1 && tam < 4) {
				campo.value = vr.substr(0, 2) + '.'
						+ vr.substr(2);
			}
			if (tam > 4 && tam < 7) {
				campo.value = vr.substr(0, 2) + '.'
						+ vr.substr(2, 3) + '.' + vr.substr(5);
			}
			if (tam > 7 && tam < 11) {
				campo.value = vr.substr(0, 2) + '.'
						+ vr.substr(2, 3) + '.' + vr.substr(5, 3) + '/'
						+ vr.substr(8);
			}
			if (tam > 11 && tam <= 14) {
				campo.value = vr.substr(0, 2) + '.'
						+ vr.substr(2, 3) + '.' + vr.substr(5, 3) + '/'
						+ vr.substr(8, 4) + "-" + vr.substr(12);
			}
		}
		// usado no onChange para completar se o usuario tiver colado o numero
		if (campo.value.length == 14) {
			var cnpj = campo.value;
			cnpj = cnpj.substr(0, 2) + '.' + cnpj.substr(2, 3) + '.'
					+ cnpj.substr(5, 3) + '/' + cnpj.substr(8, 4) + "-"
					+ cnpj.substr(12);
			campo.value = cnpj;
		}
	}
}