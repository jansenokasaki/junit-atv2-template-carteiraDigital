import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.DigitalWallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SaldoInicial {
    private DigitalWallet wallet;

    @BeforeEach
    void setUp() {
        wallet = null;
    }

    @Test
    void deveConfigurarSaldoInicialCorreto() {
        wallet = new DigitalWallet("Jansen", 100.0);
        assertEquals(100.0, wallet.getBalance());
    }

    @Test
    public void deveLancarExcecaoParaSaldoInicialNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new DigitalWallet("Jansen", -20.0));
    }
}