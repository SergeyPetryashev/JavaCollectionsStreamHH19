package tasks;

import common.Area;
import common.Person;
import common.Task;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 implements Task {

  private Set<String> getPersonDescriptions(Collection<Person> persons,
                                            Map<Integer, Set<Integer>> personAreaIds,
                                            Collection<Area> areas) {
    Set<String> personDescription = new HashSet<>();
    Map<Integer, String> personIdName = new HashMap<>();
    persons.forEach(p->personIdName.put(p.getId(), p.getFirstName()));
    Map<Integer, String> areaIdName = new HashMap<>();
    areas.forEach(area->areaIdName.put(area.getId(), area.getName()));// используем дополнительную память, и избавляемся от 1 вложености циклов
    for(Map.Entry<Integer, Set<Integer>> entry : personAreaIds.entrySet()) {
      for (Integer areaId : entry.getValue()) {
        personDescription.add(personIdName.get(entry.getKey()) + (" - ") + areaIdName.get(areaId));
      }
    }
    return personDescription;//*/
   /* return persons.stream()
            .map(p->areas.stream()
                    .filter(a->personAreaIds.get(p.getId()).contains(a.getId()))
                    .map(a->p.getFirstName()+" - "+a.getName())
                    .collect(Collectors.toSet()))
            .flatMap(x->x.stream())
            .collect(Collectors.toSet());//*/
 }

  @Override
  public boolean check() {
    List<Person> persons = List.of(
        new Person(1, "Oleg", Instant.now()),
        new Person(2, "Vasya", Instant.now())
    );
    Map<Integer, Set<Integer>> personAreaIds = Map.of(1, Set.of(1, 2), 2, Set.of(2, 3));
    List<Area> areas = List.of(new Area(1, "Moscow"), new Area(2, "Spb"), new Area(3, "Ivanovo"));
    return getPersonDescriptions(persons, personAreaIds, areas)
        .equals(Set.of("Oleg - Moscow", "Oleg - Spb", "Vasya - Spb", "Vasya - Ivanovo"));
  }
}
