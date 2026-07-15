package gui;

import com.toedter.calendar.JDateChooser;
import controller.Controller;
import model.*;
import utilities.InconsistencyException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;

import static utilities.ExceptionHandler.*;

public class SecondHomework {
    private Controller controller;
    private JPanel panel;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton docenteBtn;
    private JButton studenteBtn;
    private Studente studente;
    private Docente docente;
    // Source - https://stackoverflow.com/a/16782219
// Posted by Joel Christophel, modified by community. See post 'Timeline' for change history
// Retrieved 2026-06-30, License - CC BY-SA 3.0

    private Component[] getComponents(Component container) {
        ArrayList<Component> list;

        try {
            list = new ArrayList<>(Arrays.asList(
                    ((Container) container).getComponents()));
            for (int index = 0; index < list.size(); index++) {
                list.addAll(Arrays.asList(getComponents(list.get(index))));
            }
        } catch (ClassCastException e) {
            list = new ArrayList<>();
        }

        return list.toArray(new Component[list.size()]);
    }

    public SecondHomework() {
        String back="Indietro";
        try {
            controller = new Controller();
        }
        catch (InconsistencyException e) {
            handleInconsistencyException(e);
        }
        // Bottone Docente
        docenteBtn.addActionListener(e -> {

            // Salvataggio valori in variabili
            String login = loginField.getText();

            // Password da JPasswordField
            String password = new String(passwordField.getPassword());

            // Stampa di prova
            System.out.println("LOGIN: " + login);
            System.out.println("PASSWORD: " + password);

            JFrame docenteFrame = new JFrame("Finestra Docente");
            docenteFrame.setSize(1000, 200);
            docenteFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            try {
                docente=(Docente)controller.login(login,password,true);
                if(docente!=null){
                    JPanel p = new JPanel();
                    p.add(new JLabel("Benvenuto, "+login));
                    JButton btnLogout = new JButton("Logout");
                    btnLogout.addActionListener(e2 -> {
                        try{
                            controller.logout(docente,true);
                        } catch (InconsistencyException ex) {
                            handleInconsistencyException(ex);
                        }
                        docenteFrame.dispose();
                    });
                    JButton btnAllTirocinio = new JButton("Mostra tirocini");
                    btnAllTirocinio.addActionListener(e3->{
                        JPanel tirociniPanel = new JPanel();
                        tirociniPanel.add(new JLabel("Lista Tirocini"));
                        JTextArea tirociniTextArea = new JTextArea();
                        tirociniTextArea.setSize(600, 200);
                        tirociniTextArea.setEditable(false);
                        tirociniTextArea.setLineWrap(true);
                        StringBuilder t=new StringBuilder();
                        for(Tirocinio x : controller.getTirocini(docente,true)){
                                t.append(x.toString());
                        }
                        tirociniTextArea.setText(t.toString());
                        tirociniPanel.add(new JScrollPane(tirociniTextArea));
                        JButton btnIndietro = new JButton(back);
                        btnIndietro.addActionListener(e2 -> {
                            docenteFrame.remove(tirociniPanel);
                            docenteFrame.setContentPane(p);
                            docenteFrame.setVisible(true);
                        });
                        tirociniPanel.add(btnIndietro);
                        docenteFrame.setContentPane(tirociniPanel);
                        docenteFrame.setVisible(true);
                    });
                    JButton btnNewTirocinio = new JButton("Inserisci tirocinio(interno)");
                    btnNewTirocinio.addActionListener(e3->{
                        JLabel stato=new JLabel("Stato inserimento..");
                        JPanel tirociniPanel = new JPanel();
                        tirociniPanel.add(new JLabel("Nome"));
                        JTextField nomeTextField = new JTextField(62);
                        tirociniPanel.add(nomeTextField);
                        JTextArea descrizioneTextField = new JTextArea(4,63);
                        tirociniPanel.add(new JLabel("Descrizione"));
                        tirociniPanel.add(descrizioneTextField);
                        JDateChooser dataChooser = new JDateChooser();
                        tirociniPanel.add(new JLabel("Data"));
                        tirociniPanel.add(dataChooser);
                        JButton btnInserisci = new JButton("Inserisci");
                        btnInserisci.addActionListener(e5->{
                            try{
                                if(controller.nuovoTirocinioInterno(docente, nomeTextField.getText(), descrizioneTextField.getText(), LocalDate.ofInstant(dataChooser.getDate().toInstant(), ZoneId.systemDefault())))
                                   stato.setText("Inserito correttamente");
                                else
                                   stato.setText("Non inserito correttamente");
                            }
                            catch(NullPointerException ex){
                                stato.setText("Inserimento vuoto, non inserito correttamente");
                                handleNullPointerException(ex);
                            }
                        });
                        JButton btnIndietro = new JButton(back);
                        btnIndietro.addActionListener(e2 -> {
                            docenteFrame.remove(tirociniPanel);
                            docenteFrame.setContentPane(p);
                            docenteFrame.setVisible(true);
                        });
                        tirociniPanel.add(btnInserisci);
                        tirociniPanel.add(btnIndietro);
                        tirociniPanel.add(stato);
                        docenteFrame.setContentPane(tirociniPanel);
                        docenteFrame.setVisible(true);

                    });
                    JButton btnNewTirocinioEsterno = new JButton("Inserisci tirocinio(esterno)");
                    btnNewTirocinioEsterno.addActionListener(e3->{
                        JPanel tirociniPanel = new JPanel();
                        JLabel stato=new JLabel("Stato inserimento..");
                        tirociniPanel.add(new JLabel("Nome"));
                        JTextField nomeTextField = new JTextField(62);
                        tirociniPanel.add(nomeTextField);
                        tirociniPanel.add(new JLabel("Referente"));
                        JTextField referenteTextField = new JTextField(62);
                        tirociniPanel.add(referenteTextField);
                        tirociniPanel.add(new JLabel("Nome Azienda"));
                        JTextField nomeAziendaTextField = new JTextField(62);
                        tirociniPanel.add(nomeAziendaTextField);
                        JTextArea descrizioneTextField = new JTextArea(4,63);
                        tirociniPanel.add(new JLabel("Descrizione"));
                        tirociniPanel.add(descrizioneTextField);
                        JDateChooser dataChooser = new JDateChooser();
                        tirociniPanel.add(new JLabel("Data"));
                        tirociniPanel.add(dataChooser);
                        JButton btnInserisci = new JButton("Inserisci");
                        btnInserisci.addActionListener(e5->{
                            try{
                                if(controller.nuovoTirocinioEsterno(docente, nomeTextField.getText(), descrizioneTextField.getText(), LocalDate.ofInstant(dataChooser.getDate().toInstant(), ZoneId.systemDefault()),nomeAziendaTextField.getText(),referenteTextField.getText()))
                                    stato.setText("Inserito correttamente");
                                else
                                    stato.setText("Non inserito correttamente");
                            }
                            catch(NullPointerException ex){
                                stato.setText("Inserimento vuoto, non inserito correttamente");
                                handleNullPointerException(ex);
                            }
                        });
                        JButton btnIndietro = new JButton(back);
                        btnIndietro.addActionListener(e2 -> {
                            docenteFrame.remove(tirociniPanel);
                            docenteFrame.setContentPane(p);
                            docenteFrame.setVisible(true);
                        });
                        tirociniPanel.add(btnInserisci);
                        tirociniPanel.add(btnIndietro);
                        tirociniPanel.add(stato);
                        docenteFrame.setContentPane(tirociniPanel);
                        docenteFrame.setVisible(true);

                    });
                    JButton btnModificaStatoRichiesta = new JButton("Modifica stato richiesta");
                    btnModificaStatoRichiesta.addActionListener(e3->{
                        JPanel richiestaPanel = new JPanel();
                        JLabel stato=new JLabel("Stato modifica..");
                        richiestaPanel.add(new JLabel("Nome"));
                        JTextField nomeTextField = new JTextField(62);
                        richiestaPanel.add(nomeTextField);
                        richiestaPanel.add(new JLabel("Login Studente"));
                        JTextField loginTextField = new JTextField(62);
                        richiestaPanel.add(loginTextField);
                        JDateChooser dataChooser = new JDateChooser();
                        richiestaPanel.add(new JLabel("Data Tirocinio"));
                        richiestaPanel.add(dataChooser);
                        JButton btnAccetta = new JButton("Accetta");
                        btnAccetta.addActionListener(e5->{
                            try{
                                if(controller.modificaStatoRichiesta(docente,controller.cercaRichiesta(controller.cercaStudente(loginTextField.getText()),controller.cercaTirocinio(nomeTextField.getText(), LocalDate.ofInstant(dataChooser.getDate().toInstant(), ZoneId.systemDefault()),docente)),true))
                                    stato.setText("Modificato correttamente");
                                else
                                    stato.setText("Non modificato correttamente");
                            }
                            catch(NullPointerException ex){
                                stato.setText("Non modificato correttamente, dati non corretti");
                                handleNullPointerException(ex);
                            } catch (InconsistencyException ex) {
                                stato.setText("Non modificato correttamente,errore di consistenza dati");
                            }
                        });
                        JButton btnRifiuta = new JButton("Rifiuta");
                        btnRifiuta.addActionListener(e5->{
                            try{
                                if(controller.modificaStatoRichiesta(docente,controller.cercaRichiesta(controller.cercaStudente(loginTextField.getText()),controller.cercaTirocinio(nomeTextField.getText(), LocalDate.ofInstant(dataChooser.getDate().toInstant(), ZoneId.systemDefault()),docente)),false))
                                    stato.setText("Modificato correttamente");
                                else
                                    stato.setText("Non modificato correttamente");
                            }
                            catch(NullPointerException ex){
                                stato.setText("Non modificato correttamente, dati non corretti");
                                handleNullPointerException(ex);
                            } catch (InconsistencyException ex) {
                                stato.setText("Non modificato correttamente,errore di consistenza dati");
                            }
                        });
                        JButton btnIndietro = new JButton(back);
                        btnIndietro.addActionListener(e2 -> {
                            docenteFrame.remove(richiestaPanel);
                            docenteFrame.setContentPane(p);
                            docenteFrame.setVisible(true);
                        });
                        richiestaPanel.add(btnAccetta);
                        richiestaPanel.add(btnRifiuta);
                        richiestaPanel.add(btnIndietro);
                        richiestaPanel.add(stato);
                        docenteFrame.setContentPane(richiestaPanel);
                        docenteFrame.setVisible(true);
                    });
                    JButton btnModificaStatoTesi = new JButton("Modifica stato tesi");
                    btnModificaStatoTesi.addActionListener(e3->{
                        JPanel tesiPanel = new JPanel();
                        JLabel stato=new JLabel("Stato modifica..");
                        tesiPanel.add(new JLabel("Nome"));
                        JTextField nomeTextField = new JTextField(62);
                        tesiPanel.add(nomeTextField);
                        tesiPanel.add(new JLabel("Login Studente"));
                        JTextField loginTextField = new JTextField(62);
                        tesiPanel.add(loginTextField);
                        JDateChooser dataChooser = new JDateChooser();
                        tesiPanel.add(new JLabel("Data Tirocinio"));
                        tesiPanel.add(dataChooser);
                        JButton btnAccetta = new JButton("Accetta");
                        btnAccetta.addActionListener(e5->{
                            try{
                                if(controller.modificaStatoTesi(docente,controller.cercaTesi(controller.cercaRichiesta(controller.cercaStudente(loginTextField.getText()),controller.cercaTirocinio(nomeTextField.getText(), LocalDate.ofInstant(dataChooser.getDate().toInstant(), ZoneId.systemDefault()),docente))),true))
                                    stato.setText("Modificato correttamente");
                                else
                                    stato.setText("Non modificato correttamente");
                            }
                            catch(NullPointerException ex){
                                stato.setText("Non modificato correttamente, dati non corretti");
                                handleNullPointerException(ex);
                            } catch (InconsistencyException ex) {
                                stato.setText("Non modificato correttamente,errore di consistenza dati");
                            }
                        });
                        JButton btnRifiuta = new JButton("Rifiuta");
                        btnRifiuta.addActionListener(e5->{
                            try{
                                if(controller.modificaStatoTesi(docente,controller.cercaTesi(controller.cercaRichiesta(controller.cercaStudente(loginTextField.getText()),controller.cercaTirocinio(nomeTextField.getText(), LocalDate.ofInstant(dataChooser.getDate().toInstant(), ZoneId.systemDefault()),docente))),false))
                                    stato.setText("Modificato correttamente");
                                else
                                    stato.setText("Non modificato correttamente");
                            }
                            catch(NullPointerException ex){
                                stato.setText("Non modificato correttamente, dati non corretti");
                                handleNullPointerException(ex);
                            } catch (InconsistencyException ex) {
                                stato.setText("Non modificato correttamente,errore di consistenza dati");
                            }
                        });
                        JButton btnIndietro = new JButton(back);
                        btnIndietro.addActionListener(e2 -> {
                            docenteFrame.remove(tesiPanel);
                            docenteFrame.setContentPane(p);
                            docenteFrame.setVisible(true);
                        });
                        tesiPanel.add(btnAccetta);
                        tesiPanel.add(btnRifiuta);
                        tesiPanel.add(btnIndietro);
                        tesiPanel.add(stato);
                        docenteFrame.setContentPane(tesiPanel);
                        docenteFrame.setVisible(true);
                    });
                    p.add(btnLogout);
                    p.add(btnAllTirocinio);
                    p.add(btnNewTirocinio);
                    p.add(btnNewTirocinioEsterno);
                    p.add(btnModificaStatoRichiesta);
                    p.add(btnModificaStatoTesi);
                    docenteFrame.setContentPane(p);
                    docenteFrame.setSize(650,400);
                    docenteFrame.setVisible(true);
                    for (Component x : getComponents(panel)) {
                        x.setEnabled(false);
                    }
                    docenteFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            for (Component x : getComponents(panel)) {
                                x.setEnabled(true);
                            }
                        }
                    });
                }
            } catch (InconsistencyException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Bottone Studente
        studenteBtn.addActionListener(e -> {

            // Salvataggio valori in variabili
            String login = loginField.getText();

            // Password da JPasswordField
            String password = new String(passwordField.getPassword());

            // Stampa di prova
            System.out.println("LOGIN: " + login);
            System.out.println("PASSWORD: " + password);

            JFrame studenteFrame = new JFrame("Finestra Studente");
            studenteFrame.setSize(300, 200);
            studenteFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            try {
                studente=(Studente) controller.login(login,password,false);
                if(studente!=null){
                    JPanel p = new JPanel();
                    p.add(new JLabel("Benvenuto, "+login));
                    JButton btnLogout = new JButton("Logout");
                    btnLogout.addActionListener(e2 -> {
                        try{
                            controller.logout(studente,false);
                        } catch (InconsistencyException ex) {
                            handleInconsistencyException(ex);
                        }
                        studenteFrame.dispose();
                    });
                    //roba
                    JButton btnAllTirocinio = new JButton("Mostra tirocini");
                    btnAllTirocinio.addActionListener(e3->{
                        JPanel tirociniPanel = new JPanel();
                        tirociniPanel.add(new JLabel("Lista Tirocini"));
                        JTextArea tirociniTextArea = new JTextArea();
                        tirociniTextArea.setSize(600, 200);
                        tirociniTextArea.setEditable(false);
                        tirociniTextArea.setLineWrap(true);
                        StringBuilder t=new StringBuilder();
                        for(Tirocinio x : controller.getTirocini(studente,false)){
                            t.append(x.toString());
                        }
                        tirociniTextArea.setText(t.toString());
                        tirociniPanel.add(new JScrollPane(tirociniTextArea));
                        JButton btnIndietro = new JButton(back);
                        btnIndietro.addActionListener(e2 -> {
                            studenteFrame.remove(tirociniPanel);
                            studenteFrame.setContentPane(p);
                            studenteFrame.setVisible(true);
                        });
                        tirociniPanel.add(btnIndietro);
                        studenteFrame.setContentPane(tirociniPanel);
                        studenteFrame.setVisible(true);
                    });
                    JButton btnNewRicheista = new JButton("Inserisci richiesta");
                    btnNewRicheista.addActionListener(e3-> {
                        JLabel stato = new JLabel("Stato inserimento..");
                        JPanel richiestaPanel = new JPanel();
                        richiestaPanel.add(new JLabel("Nome Tirocinio"));
                        JTextField nomeTextField = new JTextField(62);
                        richiestaPanel.add(nomeTextField);
                        richiestaPanel.add(new JLabel("Login Docente"));
                        JTextField loginTextField = new JTextField(62);
                        richiestaPanel.add(loginTextField);
                        JDateChooser dataChooser = new JDateChooser();
                        richiestaPanel.add(new JLabel("Data Tirocinio"));
                        richiestaPanel.add(dataChooser);
                        JButton btnInserisci = new JButton("Inserisci");
                        btnInserisci.addActionListener(e5 -> {
                            try {
                                if (controller.faiRichiesta(studente,controller.cercaTirocinio(nomeTextField.getText(), LocalDate.ofInstant(dataChooser.getDate().toInstant(), ZoneId.systemDefault()),controller.cercaDocente(loginTextField.getText()))))
                                    stato.setText("Inserito correttamente");
                                else
                                    stato.setText("Non inserito correttamente");
                            } catch(InconsistencyException exe){
                                stato.setText("Inserimento impossibile,errore di consistenza dati");
                                handleInconsistencyException(exe);
                            }
                            catch (NullPointerException ex) {
                                stato.setText("Inserimento vuoto, non inserito correttamente");
                                handleNullPointerException(ex);
                            }
                        });
                        JButton btnIndietro = new JButton(back);
                        btnIndietro.addActionListener(e2 -> {
                            studenteFrame.remove(richiestaPanel);
                            studenteFrame.setContentPane(p);
                            studenteFrame.setVisible(true);
                        });
                        richiestaPanel.add(btnInserisci);
                        richiestaPanel.add(btnIndietro);
                        richiestaPanel.add(stato);
                        studenteFrame.setContentPane(richiestaPanel);
                        studenteFrame.setVisible(true);

                    });
                    JButton btnViewRichieste= new JButton("Visualizza richieste");
                    btnViewRichieste.addActionListener(e4->{
                        JPanel richiestaPanel = new JPanel();
                        richiestaPanel.add(new JLabel("Lista richieste"));
                        JTextArea richiestaTextArea = new JTextArea();
                        richiestaTextArea.setSize(600, 200);
                        richiestaTextArea.setEditable(false);
                        richiestaTextArea.setLineWrap(true);
                        StringBuilder t=new StringBuilder();
                        for(Richiesta x : controller.getRichiesta(studente,false)){
                             t.append(x.toString());
                        }
                        richiestaTextArea.setText(t.toString());
                        richiestaPanel.add(new JScrollPane(richiestaTextArea));
                        JButton btnIndietro = new JButton(back);
                        btnIndietro.addActionListener(e2 -> {
                            studenteFrame.remove(richiestaPanel);
                            studenteFrame.setContentPane(p);
                            studenteFrame.setVisible(true);
                       });
                       richiestaPanel.add(btnIndietro);
                       studenteFrame.setContentPane(richiestaPanel);
                       studenteFrame.setVisible(true);
                    });
                    JButton btnAggiungiTesi = new JButton("Aggiungi tesi");
                    btnAggiungiTesi.addActionListener(e3-> {
                        JLabel stato = new JLabel("Stato inserimento..");
                        JPanel tesiPanel = new JPanel();
                        tesiPanel.add(new JLabel("Nome Tirocinio"));
                        JTextField nomeTextField = new JTextField(62);
                        tesiPanel.add(nomeTextField);
                        tesiPanel.add(new JLabel("Login Docente"));
                        JTextField loginTextField = new JTextField(62);
                        tesiPanel.add(loginTextField);
                        JDateChooser dataChooser = new JDateChooser();
                        tesiPanel.add(new JLabel("Contenuto tesi"));
                        JTextField contenutoTextField = new JTextField(62);
                        tesiPanel.add(contenutoTextField);
                        tesiPanel.add(new JLabel("Data Tirocinio"));
                        tesiPanel.add(dataChooser);
                        JButton btnInserisci = new JButton("Inserisci");
                        btnInserisci.addActionListener(e5 -> {
                            try {
                                if (controller.aggiungiTesi(studente,controller.cercaRichiesta(studente,controller.cercaTirocinio(nomeTextField.getText(), LocalDate.ofInstant(dataChooser.getDate().toInstant(), ZoneId.systemDefault()),controller.cercaDocente(loginTextField.getText()))),contenutoTextField.getText()))
                                    stato.setText("Inserito correttamente");
                                else
                                    stato.setText("Non inserito correttamente");
                            } catch(InconsistencyException exe){
                                stato.setText("Inserimento impossibile,errore di consistenza dati");
                                handleInconsistencyException(exe);
                            }
                            catch (NullPointerException ex) {
                                stato.setText("Inserimento vuoto, non inserito correttamente");
                                handleNullPointerException(ex);
                            }
                        });
                        JButton btnIndietro = new JButton(back);
                        btnIndietro.addActionListener(e2 -> {
                            studenteFrame.remove(tesiPanel);
                            studenteFrame.setContentPane(p);
                            studenteFrame.setVisible(true);
                        });
                        tesiPanel.add(btnInserisci);
                        tesiPanel.add(btnIndietro);
                        tesiPanel.add(stato);
                        studenteFrame.setContentPane(tesiPanel);
                        studenteFrame.setVisible(true);

                    });
                    //endroba
                    p.add(btnLogout);
                    p.add(btnAllTirocinio);
                    p.add(btnNewRicheista);
                    p.add(btnViewRichieste);
                    p.add(btnAggiungiTesi);
                    studenteFrame.setContentPane(p);
                    studenteFrame.setSize(650,400);
                    studenteFrame.setVisible(true);
                    for (Component x : getComponents(panel)) {
                        x.setEnabled(false);
                    }
                    studenteFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            for (Component x : getComponents(panel)) {
                                x.setEnabled(true);
                            }
                        }
                    });
                }
            } catch (InconsistencyException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public static void main(String[] args) {

        JFrame loginPage = new JFrame("Login Page");
        loginPage.setContentPane(new SecondHomework().panel);
        loginPage.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginPage.pack();
        loginPage.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        loginField = new JTextField();
        panel.add(loginField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        passwordField = new JPasswordField();
        panel.add(passwordField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        docenteBtn = new JButton();
        docenteBtn.setText("Docente");
        panel.add(docenteBtn, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        studenteBtn = new JButton();
        studenteBtn.setText("Studente");
        panel.add(studenteBtn, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Email");
        panel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Password");
        panel.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}