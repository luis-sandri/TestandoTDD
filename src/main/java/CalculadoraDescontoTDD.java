public class CalculadoraDescontoTDD {

    public double calcular(boolean isVIP, double valor) {
        if (valor > 500.00) {
            return valor * 0.90;
        }
        return valor;
    }
}
