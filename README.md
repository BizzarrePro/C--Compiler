C- Compiler
====
简介
----
  该项目是一个具备轻量级文本编辑器功能的C-语言编译器，项目由两部分组成，编译器和文本编辑器。分别在src/team/weird/compiler/cminus\<br>
  和src/team/weird/compiler/editor路径下，目前还未将两部分整合。\<br>
  编译器部分对于C-语言的定义来自·Compiler Construction Principles and Practice·给出的文法，为了适应语法分析规则，本人对文法做出了\<br>
  一定的修改，所做的修改主要针对消除文法二义性。\<br>
  编辑器部分UI仿照Sublime编辑器，采用JMenu+JTabbedPane+JTextArea的设计，实现了一般编辑器的大多数功能。\<br>
编译器
-----
  ##词法分析
    采用按字节读入的方式，并增加了一个向前看符号，用于对操作符提取，C-语言的保留字定义在lexer/ReservedWord.java中，词法分析过程在lexer/Lexer.java中，该过程主要用于滤除注释，组成词素，并将词法单元序列保存至tokenList中用于语法分析。
  ##语法分析
    该过程采用LL(1)语法分析方式
