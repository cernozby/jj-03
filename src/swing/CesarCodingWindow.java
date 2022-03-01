package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

class CesarCodingWindow extends JFrame {
    private final JPanel mainPanel = new JPanel();
    private final JTextArea cesarInput = new JTextArea(10, 30);
    private final JTextArea cesarOutput = new JTextArea(10, 30);
    private final JTextArea shift = new JTextArea(1, 2);
    private final JMenuItem menuItemExit = new JMenuItem("Exit");
    private final JMenuItem menuItemEncode = new JMenuItem("Zasifrovat");
    private final JMenuItem menuItemDecode = new JMenuItem("Rozsifrovat");
    private final JButton encoding = new JButton("Zasifrovat");
    private final JButton decoding = new JButton("Rozsifrovat");

    public CesarCodingWindow() {
        this.pageSetting();
        this.setMenu();
        this.setMainPage();
        this.setActionListener();

    }

    private void setActionListener() {
        encoding.addActionListener(this::onEncodeAction);
        decoding.addActionListener(this::onDecodeAction);
        menuItemEncode.addActionListener(this::onEncodeAction);
        menuItemEncode.addActionListener(this::onEncodeAction);
        menuItemExit.addActionListener(this::exitAction);
    }

    private void setMenu() {
        JMenuBar mainMenu = new JMenuBar();
        JMenu menuFile = new JMenu("Menu");
        menuFile.setMnemonic(KeyEvent.VK_F);

        menuFile.add(menuItemExit);
        menuFile.add(menuItemEncode);
        menuFile.add(menuItemDecode);
        mainMenu.add(menuFile);
        this.setJMenuBar(mainMenu);
    }

    private void setMainPage() {

        mainPanel.add(textArea("Zasifrovat", this.cesarInput));
        mainPanel.add(textArea("Rozsifrovat",this.cesarOutput));
        mainPanel.add(encoding);
        mainPanel.add(textArea("posunuti", this.shift));
        mainPanel.add(decoding);
        this.setContentPane(mainPanel);
    }

    private JPanel textArea(String labelName, JTextArea input) {
        JPanel textArea= new JPanel();
        textArea.setLayout(new BoxLayout(textArea, BoxLayout.Y_AXIS));
        JLabel myLabel = new JLabel(labelName);
        JScrollPane scrollPanel = new JScrollPane(input);
        scrollPanel.setBackground(Color.white);
        textArea.add(myLabel);
        textArea.add(scrollPanel);

        return textArea;
    }

    void pageSetting() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(700, 700));
        this.pack();
    }

    private void makeDialog(String message) {
        JFrame jFrame = new JFrame();
        JDialog dialog = new JDialog(jFrame, "Error", true);
        dialog.setLayout( new FlowLayout() );
        JButton b = new JButton ("OK");
        b.addActionListener ( new ActionListener() {public void actionPerformed( ActionEvent e ) {dialog.setVisible(false);}});
        dialog.add( new JLabel (message));
        dialog.add(b);
        dialog.setSize(500,200);
        dialog.setVisible(true);
    }

    private void onDecodeAction(ActionEvent e) {
        try {
            String currentText = cesarOutput.getText();
            int shift = Integer.parseInt(this.shift.getText());
            cesarInput.setText(shiftText(currentText, -1 * shift));
        } catch (NumberFormatException ex) {
            this.makeDialog(this.shift.getText() + " neni validni cislo.");
        } catch (IllegalArgumentException ex) {
            this.makeDialog(ex.getMessage());
        }
    }

    private void onEncodeAction(ActionEvent e) {
        try {
            String currentText = cesarInput.getText();
            int shift = Integer.parseInt(this.shift.getText());
            cesarOutput.setText(shiftText(currentText, shift));
        } catch (NumberFormatException ex) {
            this.makeDialog(this.shift.getText() + " neni validni cislo.");
        } catch (IllegalArgumentException ex) {this.makeDialog(ex.getMessage());}
    }

    private void exitAction(ActionEvent e) {
        setVisible(false);
        dispose();
    }


    private char getCharvalue(int ch, int shift) {
        int resutl = (ch - 65 + shift) % 26 + 65;
        while(resutl < 65) {
            System.out.print((char)(96) - (65 - resutl));
            resutl = (91) - (65 - resutl);
        }
        return (char)resutl;
    }

    private String shiftText(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            if (Character.isUpperCase(text.charAt(i))) {
                result.append(getCharvalue(text.charAt(i), shift));
            } else if ((int)text.charAt(i) == 46 || (int)text.charAt(i) == 32) {
                result.append(text.charAt(i));
            } else {
                throw new IllegalArgumentException("Text může obsahovat pouze písmena A-Z, mezeru a tecku");
            }
        }
        return result.toString();
    }
}