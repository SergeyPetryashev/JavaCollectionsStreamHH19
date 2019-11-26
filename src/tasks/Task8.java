package tasks;

import common.Person;
import common.Task;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  public List<String> getNames(List<Person> persons) {
    return persons.stream().skip(1)
            .map(Person::getFirstName)
            .collect(Collectors.toList());// добавили skip(1) удалили лишний код
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {
    return getNames(persons).stream().collect(Collectors.toSet());// лишний вызов distinct(), мы и так сохраняем в Set
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  public String convertPersonToString(Person person) {  //Воспользовался stream -> избавился от дублирования кода.
    return Stream.of(person.getSecondName(), person.getFirstName(), person.getMiddleName())
            .filter(s -> s!=null)
            .collect(Collectors.joining(" "));  // Заменил повтор добавления SecondName на MiddleName.
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
            .collect(Collectors.toMap(Person::getId, person -> convertPersonToString(person), (existing, replace) ->existing));  // замена на stream с конвертацией в словарь
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    return persons1.stream().anyMatch(persons2::contains);  // заменил проверку с применением stream. Поиск идет до первого элемента удовлетворяющего условию
  }

  //Выглядит вроде неплохо...
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count(); //размер можно запросить одной командой, ни к чему лишняя переменная
  }

  @Override
  public boolean check() {
   // System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = true;
    boolean reviewerDrunk = false;
    boolean FutureCoolProgrammer=false;

    Instant time = Instant.now();

    Collection<Person> persons = new ArrayList<>();
    persons.add(new Person(0, "FalsePerson", time));
    int n=3;
    for(int i=1; i<n; i++){
      persons.add(new Person(i, "Person "+i, time.plusSeconds(i)));
    }
    persons.add(new Person(n, "Person 1","Vip", time.plusSeconds(n)));
    List<String> listAllNamePerson = List.of("Person 1", "Person 2", "Person 1");

    Collection<Person> persons2 = new ArrayList<>();
    persons2.add(new Person(0, "FalsePerson", time.minusSeconds(1)));

    Map<Integer, String> idPersonNames= new HashMap<>();
    idPersonNames.put(0, "FalsePerson");
    idPersonNames.put(1, "Person 1");
    idPersonNames.put(2, "Person 2");
    idPersonNames.put(3, "Vip Person 1");

    Stream<Integer> numbers = Stream.of(1,2,3,4,5,6);
    if(getNames((List<Person>) persons).equals(listAllNamePerson)
            && getDifferentNames((List<Person>) persons).equals(new HashSet<String>(listAllNamePerson))
            &&  getPersonNames(persons).equals(idPersonNames)
            && hasSamePersons(persons, persons2)==false
            && countEven(numbers)==3)
      FutureCoolProgrammer=true;

    return FutureCoolProgrammer;
    //return codeSmellsGood || reviewerDrunk;
  }
}
