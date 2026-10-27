public class CalculadoraDescontoTDD {

    public double calcular(boolean isVIP, double valor) {
        if (valor <= 0.00) {
            throw new IllegalArgumentException("O valor total dos itens deve ser maior que zero.");
        }
        double valorFinal = valor;
        if (valor > 1000.00) {
            valorFinal = valor * 0.80;
        } else if (valor > 500.00) {
            valorFinal = valor * 0.90;
        }
        if (isVIP) {
            valorFinal *= 0.95;
        }
        if (valorFinal < 100.00) {
            valorFinal += 15.00;
        }
        return valorFinal;
    }
}
