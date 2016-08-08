C- Compiler
========
<br>
## 简介


  &emsp;&emsp;该项目是一个具备轻量级文本编辑器功能的C-语言编译器，项目由两部分组成，编译器和文本编辑器。<br>
  &emsp;&emsp;编译器部分对于C-语言的定义来自 *Compiler Construction Principles and Practice* 给出的文法，为了适应语法分析规则，对文法做出了一定的修改，所做的修改主要针对消除文法二义性。<br>
  &emsp;&emsp;编辑器部分UI仿照Sublime编辑器，采用JMenu+JTabbedPane+JTextArea的设计，实现了一般编辑器的大多数功能。<br>
## 编译器结构
### 词法分析
    
&emsp;&emsp;词法分析主要用于滤除注释，组成词素，并将词法单元序列输出至语法分析。项目中采用按字节读入的方式，并增加了一个向前看符号，用于对操作符提取。C-语言的保留字放进了保留字表中，在提取标识符时查表获得。最终生成的词法单元序列保存在数组列表中，输出至语法分析。该过程由`src/team/weird/compiler/cminus/lexer`包实现。

### 语法分析
    
&emsp;&emsp;语法分析主要用于对词法分析输出的词法单元序列进行语法规则的分析，验证该串儿是否可由定义的文法生成，并最终生成语法分析树。项目中采用LL(1)文法自顶向下进行语法分析，其过程如下：<br>

* 提取产生式
* 消除左递归
* 提取左公因子
* 计算First集合
* 计算Follow集合
* 计算Select集合
* 构建预测分析表
* 预测分析
* 构建语法分析树

#### 提取产生式

&emsp;&emsp;从`production.txt`中提取产生式，并将产生式分为非终结符和产生式体，非终结符作为产生式表的`Key`，由于产生式可能对应多种推导规则，产生式体则继续划分为终结符、非终结符以及ε组成的字符串链表组成的链表，并作为产生式表的`Value`。


#### 消除左递归
>&emsp;&emsp; *//before removing left recurtion*<br>
>&emsp;&emsp;**arg-list -> arg-list , expression | expression** <br><br>
>&emsp;&emsp; *//after removing left recurtion*<br>
>&emsp;&emsp;**arg-list -> expression arg-list\`** <br>
>&emsp;&emsp;**arg-list\`-> , expression arg-list\` | empty** 

&emsp;&emsp;以上为一个消除左递归的例子

&emsp;&emsp;本项目中采用的方式是将产生式表的`Key`与产生式体的每个链表首部进行比对，若相等，则含有直接左递归，其消除方式是再构造一个新的非终结符，将原产生式体中含左递归的链表删去，将链表组中不含左递归的链表加进原产生式右部，再将所有含左递归的链表组的首部删去并在末尾加上非终结符，最终加进新的非终结符对应的产生式映射中，最后附加一个ε，消除左递归完成。
