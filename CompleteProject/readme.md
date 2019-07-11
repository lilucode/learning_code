Xml To Json分析<br>
<br>
一.已知条件
1. xml文件标签和json标签的对应关系----表格对应关系
2. 所需json文件的格式----一个json版式的文件模板
3. Xml文件大体格式----通过示例xml文件和对应关系总结所得
4. 一个测试的Xml文件----完整的xml文件
<br>
<br>
二.具体步骤：
1. FileChooser固定好操作界面,选取目标文件或者文件夹和存放文件夹,将文本框中的内容进行保存,设置按钮的点击事件;<br>
> 情况一：目标是文件
2. 如果目标是文件,启动线程FileThread,线程中会调用FileRealize类,将界面传送的源文件路径和目标路径赋给XmlToJson方法;
3. FileRealize中,通过源文件路径,使用GetXmlUtil工具类获取格式化后的文件名称（只是名称，并且对后缀名进行更改）,使用GetBean中的Bean方法获取处理过的文件,返回一个以json格式展示的String类型的字符串;
4. 调用WriteJson类中的方法,将目标路径,文件名和String类型的字符串赋给WriteJson,使用IO流将String写入文件并进行保存.
情况二：目标是文件夹
2. 如果目标是文件夹,





