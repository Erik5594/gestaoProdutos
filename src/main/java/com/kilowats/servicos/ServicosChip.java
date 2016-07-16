package com.kilowats.servicos;

import com.kilowats.dao.ChipDao;
import com.kilowats.entidades.Chip;
import com.kilowats.interfaces.IPersistirBancoDados;

public class ServicosChip {
	
	public static boolean persistirChip(Chip chip){
		IPersistirBancoDados persistir = new ChipDao();
		return persistir.salvar(chip);
	}

}
