# Grasscutter-MailHttp
Grasscutter-Mail-http-plugin

发送这个json到
```127.0.0.1:49516```
```
{
  "uid": 999,
  "title": "Title",
  "sender": "Server",
  "body": {
     "content": "test",
     "items": [
        {"id": 123, "count": 456, "level": 0},
        {"id": 123, "count": 456, "level": 0}
     ]
  }
}
```

## 关于编译
2022.2版本的idea有bug，无法正确识别java17的jar依赖
你需要使用eap版本的idea  
[下载idea-eap](https://www.jetbrains.com.cn/idea/nextversion/#section=windows)
