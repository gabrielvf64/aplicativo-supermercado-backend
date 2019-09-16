package com.gabrielferreira.aplicativo.services;

import com.gabrielferreira.aplicativo.dominio.PagamentoComBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {

    public void preencherPagamentoComBoleto(PagamentoComBoleto pagamentoComBoleto, Date dataDoPedido) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(dataDoPedido);
        calendario.add(Calendar.DAY_OF_MONTH, 7);
        pagamentoComBoleto.setDataVencimento(calendario.getTime());
    }
}
