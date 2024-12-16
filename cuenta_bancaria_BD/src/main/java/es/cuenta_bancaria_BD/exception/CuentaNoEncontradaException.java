package es.cuenta_bancaria_BD.exception;

public class CuentaNoEncontradaException extends RuntimeException{
    public CuentaNoEncontradaException(String message){
        super(message);
    }
}
