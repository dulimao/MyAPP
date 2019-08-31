class Person{

  String name;
  int age;

  Person(this.name,this.age);

  printInfo() {
    print("$name --- $age");
  }

}


void main() {

  Person person = new Person("ayao",25);
  print(person.name);
  print(person.age);
}