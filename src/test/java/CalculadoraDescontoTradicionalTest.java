import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculadoraDescontoTradicionalTest {

    private static final double TOLERANCIA = 0.000001;

    private CalculadoraDescontoTradicional calculadora;

    @BeforeEach
    void configurar() {
        calculadora = new CalculadoraDescontoTradicional();
    }

    @DisplayName("Aplica a faixa de desconto apenas acima dos limites informados")
    @ParameterizedTest(name = "Compra de R$ {0} deve resultar em R$ {1}")
    @CsvSource({
            "250.00, 250.00",
            "499.99, 499.99",
            "500.00, 500.00",
            "500.01, 450.009",
            "750.00, 675.00",
            "999.99, 899.991",
            "1000.00, 900.00",
            "1000.01, 800.008",
            "1500.00, 1200.00"
    })
    void deveAplicarDescontoConformeValorDaCompra(double valor, double esperado) {
        assertEquals(esperado, calculadora.calcular(false, valor), TOLERANCIA);
    }

    @DisplayName("Aplica os 5% VIP sobre o valor que ja recebeu o desconto da faixa")
    @ParameterizedTest(name = "Compra VIP de R$ {0} deve resultar em R$ {1}")
    @CsvSource({
            "250.00, 237.50",
            "500.00, 475.00",
            "500.01, 427.50855",
            "750.00, 641.25",
            "1000.00, 855.00",
            "1000.01, 760.0076",
            "1500.00, 1140.00"
    })
    void deveAplicarDescontoVipSobreSubtotalDescontado(double valor, double esperado) {
        assertEquals(esperado, calculadora.calcular(true, valor), TOLERANCIA);
    }

    @DisplayName("Cobra R$ 15 de frete somente quando o subtotal apos descontos e inferior a R$ 100")
    @ParameterizedTest(name = "VIP={0}, compra de R$ {1} deve resultar em R$ {2}")
    @CsvSource({
            "false, 0.01, 15.01",
            "false, 50.00, 65.00",
            "false, 99.99, 114.99",
            "false, 100.00, 100.00",
            "false, 100.01, 100.01",
            "true, 80.00, 91.00",
            "true, 100.00, 110.00",
            "true, 105.26, 114.997",
            "true, 105.27, 100.0065",
            "true, 120.00, 114.00"
    })
    void deveCalcularFreteAposTodosOsDescontos(boolean vip, double valor, double esperado) {
        // O enunciado nao define arredondamento; a tolerancia cobre a precisao do double.
        assertEquals(esperado, calculadora.calcular(vip, valor), TOLERANCIA);
    }

    @DisplayName("Rejeita totais zerados ou negativos para qualquer categoria de cliente")
    @ParameterizedTest(name = "VIP={0}, valor invalido={1}")
    @CsvSource({
            "false, 0.00",
            "true, 0.00",
            "false, -0.01",
            "true, -0.01",
            "false, -100.00",
            "true, -100.00",
            "false, -1000.00",
            "true, -1000.00"
    })
    void deveLancarIllegalArgumentExceptionParaTotalInvalido(boolean vip, double valor) {
        assertThrows(IllegalArgumentException.class, () -> calculadora.calcular(vip, valor));
    }

    @DisplayName("Cada pedido deve ser calculado independentemente do pedido anterior")
    @ParameterizedTest(name = "Apos compra VIP de R$ 1500: VIP={0}, R$ {1} deve resultar em R$ {2}")
    @CsvSource({
            "false, 50.00, 65.00",
            "false, 250.00, 250.00",
            "true, 50.00, 62.50",
            "true, 250.00, 237.50"
    })
    void naoDeveReutilizarValorDePedidoAnterior(boolean vip, double valor, double esperado) {
        calculadora.calcular(true, 1500.00);

        assertEquals(esperado, calculadora.calcular(vip, valor), TOLERANCIA);
    }
}
