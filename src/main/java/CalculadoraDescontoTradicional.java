public class CalculadoraDescontoTradicional {

    public double desconto;
    public double calcular(boolean isVIP, double valor){
        if (valor >= 500.00) {
            desconto = 0.9 * valor;
        }
        if (valor >= 1000.00) {
            desconto = 0.8 * valor;
        }
        if (isVIP) {
            desconto = desconto * 0.95;
        }
        if (desconto < 100.00){
            desconto = desconto + 15.00;
        }
        if (desconto < 0.00){
            System.out.println("Argumento inválido");
        }
        return desconto;
    }

}
