import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calc extends JFrame implements ActionListener {
    private JTextField visor;
    private Num n1 = new Num();
    private Num n2 = new Num();
    private Num res = new Num();
    private String operacao = "";
    private boolean novoNumero = true;

    public Calc() {
        setTitle("Calculadora");
        setSize(320, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        visor = new JTextField("0");
        visor.setFont(new Font("Arial", Font.BOLD, 32));
        visor.setHorizontalAlignment(JTextField.RIGHT);
        visor.setEditable(false);
        add(visor, BorderLayout.NORTH);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] botoes = {
            "C", "⌫", "%", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "√", "="
        };

        for (String texto : botoes) {
            JButton btn = new JButton(texto);
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            btn.addActionListener(this);
            painel.add(btn);
        }

        add(painel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if ("0123456789.".contains(comando)) {
            if (novoNumero) {
                visor.setText(comando.equals(".") ? "0." : comando);
                novoNumero = false;
            } else {
                if (comando.equals(".") && visor.getText().contains(".")) {
                    return;
                }
                visor.setText(visor.getText() + comando);
            }
        } else if (comando.equals("C")) {
            visor.setText("0");
            n1.setValor(0);
            n2.setValor(0);
            res.setValor(0);
            operacao = "";
            novoNumero = true;
        } else if (comando.equals("⌫")) {
            String atual = visor.getText();
            if (atual.length() > 1) {
                visor.setText(atual.substring(0, atual.length() - 1));
            } else {
                visor.setText("0");
                novoNumero = true;
            }
        } else if (comando.equals("√")) {
            n1.setValor(Double.parseDouble(visor.getText()));
            res.setValor(Math.sqrt(n1.getValor()));
            exibirResultado(res.getValor());
            novoNumero = true;
        } else if (comando.equals("%")) {
            n1.setValor(Double.parseDouble(visor.getText()));
            res.setValor(n1.getValor() / 100);
            exibirResultado(res.getValor());
            novoNumero = true;
        } else if ("+-*/".contains(comando)) {
            n1.setValor(Double.parseDouble(visor.getText()));
            operacao = comando;
            novoNumero = true;
        } else if (comando.equals("=")) {
            n2.setValor(Double.parseDouble(visor.getText()));

            switch (operacao) {
                case "+":
                    res.setValor(n1.getValor() + n2.getValor());
                    break;
                case "-":
                    res.setValor(n1.getValor() - n2.getValor());
                    break;
                case "*":
                    res.setValor(n1.getValor() * n2.getValor());
                    break;
                case "/":
                    if (n2.getValor() == 0) {
                        visor.setText("Erro");
                        novoNumero = true;
                        return;
                    }
                    res.setValor(n1.getValor() / n2.getValor());
                    break;
                default:
                    res.setValor(n2.getValor());
            }

            exibirResultado(res.getValor());
            novoNumero = true;
        }
    }

    private void exibirResultado(double valor) {
        if (valor == (long) valor) {
            visor.setText(String.format("%d", (long) valor));
        } else {
            visor.setText(String.valueOf(valor));
        }
    }

    public static void main(String[] args) {
        new Calc();
    }
}
