package org.unifacisa.exceptions;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;

public class GlobalExceptionHandler {

    public static void handlePersistenceException(PersistenceException e) {
        JOptionPane.showMessageDialog(null, "Erro de persistência: " + e.getMessage());
    }

    public static void handleGeneralException(Exception e) {
        JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage());
    }

    public static void handleNoResultException(NoResultException e){
        JOptionPane.showMessageDialog(null,"Sem resultado: " + e.getMessage());
    }

    public static void handleIllegalArgumentException(String msg){
        JOptionPane.showMessageDialog(null, msg);
    }

    public static void handleRuntimeException(String msg){
        JOptionPane.showMessageDialog(null, msg);
    }
}
