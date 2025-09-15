import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.DigitalWallet;

public class Pagamento {
    private DigitalWallet wallet;

    @BeforeEach
    void setUp() {
        wallet = null;
    }

    @ParameterizedTest
    @CsvSource({
            "100.0, 30.0, true",
            "50.0, 80.0, false",
            "10.0, 10.0, true"
    })
    void pagamentoComCarteiraVerificadaENaoBloqueada(double initialBalance, double paymentAmount, boolean expected) {
        wallet = new DigitalWallet("Jansen", initialBalance);
        wallet.unlock();
        wallet.verify();

        assumeFalse(wallet.isLocked());
        assumeTrue(wallet.isVerified());

        boolean result = wallet.pay(paymentAmount);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-100.0, 0.0})
    void deveLancarExcecaoParaPagamentoInvalido(double paymentAmount) {
        wallet = new DigitalWallet("Jansen", 500.0);
        wallet.unlock();
        wallet.verify();

        assumeFalse(wallet.isLocked());
        assumeTrue(wallet.isVerified());

        assertThrows(IllegalArgumentException.class, () -> wallet.pay(paymentAmount));
    }

    @Test
    void deveLancarSeNaoVerificadaOuBloqueada() {
        wallet = new DigitalWallet("Jansen", 500.0);
        assertThrows(IllegalStateException.class, () -> wallet.pay(1000.0));
    }
}