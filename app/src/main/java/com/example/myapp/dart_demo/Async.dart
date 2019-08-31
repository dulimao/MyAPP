

import 'dart:convert';

void main() async{

  var result = await getData();
  print(result);

}

//异步方法
getData() async{


  return "下载图片";
}