

//接口
abstract class Cache<T> {
    T getByKey(String key);
    void setByKey(String key,T value);
}

//内存缓存
class MemeoryCache<T> implements Cache<T> {

  Map<String,T> _map = new Map();
  @override
  T getByKey(String key) {

    return _map[key];
  }

  @override
  void setByKey(String key, T value) {
    _map[key] = value;
  }

}

//文件缓存
class FileCache<T> implements Cache<T> {
  Map<String,T> map = new Map();
  @override
  T getByKey(String key) {

    return map[key];
  }

  @override
  void setByKey(String key, T value) {
    map[key] = value;
  }

}


void main() {
  MemeoryCache<String> cache = new MemeoryCache<String>();
  cache.setByKey("name", "axin");
  cache.setByKey("age", "22");
  print(cache.getByKey("name"));

  FileCache<Map<String,String>> fileCache = new FileCache();
  fileCache.setByKey("student", {"num" : "123","age" : "23"});
  fileCache.setByKey("school", {"name":"兰州交通大学","address" : "兰州市"});
  print(fileCache.getByKey("school"));
}