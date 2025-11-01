package ticketmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(10);
		machine.insertMoney(20);
		// THEN La balance est mise à jour, les montants sont correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
	// S3 : on n'imprime pas le ticket si le montant inséré est insuffisant
	void cannotPrintTicketWhenBalanceIsInsufficient() {
		machine.insertMoney(PRICE - 10);
		assertFalse(machine.printTicket(), "Le ticket a été imprimé alors que la balance est insuffisante");	
	}

	@Test
	// S4 : on imprime le ticket si le montant inséré est suffisant
	void canPrintTicketWhenBalanceIsSufficient() {
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket(), "Le ticket n'a pas été imprimé alors que la balance est suffisante");
	}

	@Test
	// S5 : Quand on imprime un ticket la balance est decrémentée du prix du ticket
	void printingTicketDecreasesBalance() {
		machine.insertMoney(PRICE + 20); // On insère plus que le prix du ticket
		machine.printTicket(); // On imprime un ticket
		assertEquals(20, machine.getBalance(), "La balance n'est pas correctement décrémentée après impression du ticket");
	}

	@Test
	// S6 : Le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void printingTicketIncreasesTotal() {
        machine.insertMoney(PRICE);
		assertEquals(0, machine.getTotal(),"Le total ne doit pas être mis à jour avant l'impression du ticket");
        machine.printTicket();
        assertEquals(PRICE, machine.getTotal(),"Le total doit être mis à jour après l'impression du ticket");
	}
	
	@Test
	// S7 : refund() rendcorrectement la monnaie
	void refundReturnsBalance() {
		machine.insertMoney(70);
		int refund = machine.refund();
		assertEquals(70, refund, "refund() ne rend pas correctement la monnaie");
	}

	@Test
	// S8 : refund() remet la balance à zéro
	void refundResetsBalance() {
		machine.insertMoney(70);
		machine.refund();
		assertEquals(0, machine.getBalance(), "refund() ne remet pas la balance à zéro");
	}

	@Test
	// S9 : on ne peut pas insérerun montant négatif
	void cannotInsertNegativeAmount() {
	assertThrows(IllegalArgumentException.class, () -> {
        machine.insertMoney(-10);
		}, "Insérer un montant négatif doit lever une exception");
	}	

	@Test
	// S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	void cannotCreateMachineWithNegativeTicketPrice() {
		assertThrows(IllegalArgumentException.class, () -> {
			new TicketMachine(-50);
		}, "Créer une machine avec un prix négatif doit lever une exception");
	}


}


