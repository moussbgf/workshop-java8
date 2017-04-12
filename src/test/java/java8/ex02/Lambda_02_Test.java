package java8.ex02;

import java8.data.Account;
import java8.data.Data;
import java8.data.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Exercice 02 - Map
 */
public class Lambda_02_Test {

	// tag::GenericMapper[]
	interface GenericMapper <T, P> {
		T genericMap(P p);
	}
	// end::GenericMapper[]
	
	// tag::map[]
	private <T, P> List<T> genericMap(List<P> personList, GenericMapper<T, P> genericMapper) {
		
		List<T> result = new ArrayList<>();
		
		for (P p : personList) {
			T t = genericMapper.genericMap(p);
			result.add(t);
		}
		return result;
	}
	// end::map[]

	// tag::test_map_person_to_account[]
	@Test
	public void test_map_person_to_account() throws Exception {

		List<Person> personList = Data.buildPersonList(100);

		// TODO transformer la liste de personnes en liste de comptes
		// TODO tous les objets comptes ont un solde à 100 par défaut
		List<Account> result = genericMap(personList, p -> {
			Account acc = new Account();
			acc.setOwner(p);
			acc.setBalance(100);
			return acc;
		});

		assertThat(result, hasSize(personList.size()));
		assertThat(result, everyItem(hasProperty("balance", is(100))));
		assertThat(result, everyItem(hasProperty("owner", notNullValue())));
	}
	// end::test_map_person_to_account[]

	// tag::test_map_person_to_firstname[]
	@Test
	public void test_map_person_to_firstname() throws Exception {

		List<Person> personList = Data.buildPersonList(100);

		// TODO transformer la liste de personnes en liste de prénoms
		List<String> result = genericMap(personList, p -> p.getFirstname());

		assertThat(result, hasSize(personList.size()));
		assertThat(result, everyItem(instanceOf(String.class)));
		assertThat(result, everyItem(startsWith("first")));
	}
	// end::test_map_person_to_firstname[]
}
