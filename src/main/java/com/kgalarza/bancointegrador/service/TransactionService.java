package com.kgalarza.bancointegrador.service;

import com.kgalarza.bancointegrador.model.dto.TransactionInDto;
import com.kgalarza.bancointegrador.model.dto.TransactionOutDto;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {

    TransactionOutDto makeBranchDeposit(TransactionInDto movimientoInDto);

    TransactionOutDto makeATMDeposit(TransactionInDto movimientoInDto);

    TransactionOutDto makeDepositToAnotherAccount(TransactionInDto movimientoInDto);

    TransactionOutDto makePhysicalPurchase(TransactionInDto movimientoInDto);

    TransactionOutDto makeOnlinePurchase(TransactionInDto movimientoInDto);

    TransactionOutDto makeATMWithdrawal(TransactionInDto movimientoInDto);

}
