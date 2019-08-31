

T getData<T>(T value) {
  return value;
}


void main() {
//  var v = getData<String>(" dff ");
//  print(v);
//  print(v is String);


  MyList<String> list = new MyList<String>();
  list.add("123 str");
  list.add("hello");
  list.printList();
}

class MyList<T> {
  List<T> list = new List<T>();
  void add(T value) {
    list.add(value);
  }

  void printList() {
    print(list);
  }
}