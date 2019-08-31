
import 'dart:convert' as convert;

import 'package:http/http.dart' as http;

void main() async{

  var url = "https://www.baidu.com";
  getDataAsync(url);

}

//dynamic getData(String url) => getData(url);

getDataAsync(String url) async{
  var response = await http.get(url);
  if (response.statusCode == 200) {
//    var jsonResponse = convert.jsonDecode(response.body);
    var result = response.body;
    print("数据请求成功 结果： $result");
  } else {
    print("数据请求失败了");
  }

}