package tasks;

import common.Person;
import common.PersonService;
import common.Task;

import java.util.*;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис (он выдает несортированный Set<Person>)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимпотику работы
 */
public class Task1 implements Task {

  // !!! Редактируйте этот метод !!!
  private List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = PersonService.findPersons(personIds);
    Map<Integer, Person> mapIdPerson = new HashMap<>(persons.size());
    persons.forEach(p->mapIdPerson.put(p.getId(),p));           //O(n)
    List<Person> personsSort = new ArrayList<>(persons.size());
    personIds.forEach(id->personsSort.add(mapIdPerson.get(id)));//O(n)
    return personsSort;
    //List<Person> personsList = new ArrayList<>(persons);
    //persons.sort(Comparator.comparingInt(p -> personIds.indexOf(p.getId())));// асимптотика O(n**2*log(n) так как сортировка O(nLog(n)) * indexOf O(n)
    //return persons;

  }

  @Override
  public boolean check() {
    List<Integer> ids = List.of(1, 2, 3);

    return findOrderedPersons(ids).stream()
        .map(Person::getId)
        .collect(Collectors.toList())
        .equals(ids);
  }

}
