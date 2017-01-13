package com.github.erik5594.importacao.interfaces;

import java.io.IOException;
import java.util.List;

public interface ArquivoImportacao {
	List<?> getListaDeObjetoDoArquivo(String separador) throws IOException;
}
