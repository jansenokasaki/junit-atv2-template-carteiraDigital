import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;

import com.example.DigitalWallet;



class Estorno {
    DigitalWallet dWallet;
    static Stream<Arguments> valoresEstorno() {
        return Stream.of(
            Arguments.of(100.0, 10.0, 110.0),
            Arguments.of(0.0,   5.0,   5.0),
            Arguments.of(50.0,  0.01, 50.01)
        );
    }

    @ParameterizedTest
    @MethodSource("valoresEstorno")
    void refundComCarteiraValida(double inicial, double valor, double saldoEsperado) {
        dWallet = new DigitalWallet("Jansen", inicial);
        dWallet.verify();
        dWallet.unlock();

        assumeTrue(dWallet.isVerified());
        assumeFalse(dWallet.isLocked());

        dWallet.refund(valor);

        assertEquals(saldoEsperado, dWallet.getBalance());
    }

    @ParameterizedTest
    @ValueSource(ints = {-10000, 0})
    void deveLancarExcecaoParaRefundInvalido(double valor) {
        dWallet = new DigitalWallet("Jansen", 1000);
        dWallet.unlock();
        dWallet.verify();

        assumeTrue(dWallet.isVerified());
        assumeFalse(dWallet.isLocked());
        assertThrows(IllegalArgumentException.class, () -> dWallet.refund(valor));
    }

    @Test
    void deveLancarSeNaoVerificadaOuBloqueada() {
        dWallet = new DigitalWallet("Jansen", 9000);

        assertThrows(IllegalStateException.class, () -> dWallet.refund(1110));
        
    }
}
