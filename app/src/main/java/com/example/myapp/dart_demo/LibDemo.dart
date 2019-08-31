
import 'Person1.dart';
import 'Person2.dart' as PLib;//解决库冲突

//部分导入
//import 'MyLib.dart' show getName;//只暴露getName()方法
import 'MyLib.dart' hide getName;//隐藏getName()方法

void main() {
  Person person = new Person();
  person.printInfo();

  PLib.Person person2 = new PLib.Person();
  person2.printInfo();

//  getName();

  getAge();

  getSex();


}