## 测试运行说明，用来测试pdf转换服务器的客户端程序
file.destFilePath word文件路径，file.destFilePath pdf 输出路径
### Windows
java  -Dfile.sourceFilePath=D:\doc-2-pdf\doc -Dfile.destFilePath=D:\doc-2-pdf\pdf -jar word-pdf-client.jar
### Linux
java  -Dfile.sourceFilePath=/root/word2pdf/doc -Dfile.destFilePath=/root/word2pdf/pdf -jar word-pdf-client.jar