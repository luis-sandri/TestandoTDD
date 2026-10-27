public class CalculadoraDescontoTDD {

    public double calcular(boolean isVIP, double valor) {
        if (valor > 1000.00) {
            return valor * 0.80;
        }
        if (valor > 500.00) {
            return valor * 0.90;
        }
        return valor;
    }
}
