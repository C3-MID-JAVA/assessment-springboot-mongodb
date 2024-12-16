package es.cuenta_bancaria_BD.exception;

public class SaldoInsuficienteException extends RuntimeException{
    public SaldoInsuficienteException(String message){
        super(message);
    }
}
