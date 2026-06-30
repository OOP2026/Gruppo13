package gui;

import controller.Controller;
import model.Docente;
import model.Studente;
import model.Tirocinio;
import utilities.InconsistencyException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private Docente docente;
    private Studente studente;
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
                        JPanel tirociniPanel = new JPanel();
                        tirociniPanel.add(new JLabel("Inserisci tirocinio(interno)"));
                        //ADD ROBA
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
                    JButton btnNewTirocinioEsterno = new JButton("Inserisci tirocinio(esterno)");
                    btnNewTirocinioEsterno.addActionListener(e3->{
                        System.out.println("premuto tirocinioesterno");
                        JPanel tirociniPanel = new JPanel();
                        tirociniPanel.add(new JLabel("Inserisci tirocinio(esterno)"));
                        //ADD ROBA
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
                    p.add(btnLogout);
                    p.add(btnAllTirocinio);
                    p.add(btnNewTirocinio);
                    p.add(btnNewTirocinioEsterno);
                    docenteFrame.setContentPane(p);
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
            String mail = loginField.getText();

            // Password da JPasswordField
            String password = new String(passwordField.getPassword());

            // Stampa di prova
            System.out.println("MAIL: " + mail);
            System.out.println("PASSWORD: " + password);

            JFrame studenteFrame = new JFrame("Finestra Studente");
            studenteFrame.setSize(300, 200);
            studenteFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            JPanel p = new JPanel();
            p.add(new JLabel("Benvenuto Studente"));

            studenteFrame.setContentPane(p);
            studenteFrame.setVisible(true);
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