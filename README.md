C- Compiler
========
<br>


---
  &emsp;&emsp;该项目是一个具备轻量级文本编辑器功能的C-语言编译器，项目由两部分组成，编译器和文本编辑器。<br>
  &emsp;&emsp;编译器部分对于C-语言的定义来自 *Compiler Construction Principles and Practice* 给出的文法，为了适应语法分析规则，对文法做出了一定的修改，所做的修改主要针对消除文法二义性。<br>
  &emsp;&emsp;编辑器部分UI仿照Sublime编辑器，采用JMenu+JTabbedPane+JTextArea的设计，实现了一般编辑器的大多数功能。<br>
## 编译器结构
### 词法分析
&emsp;&emsp;由`src/team/weird/compiler/cminus/lexer`包实现。<br>
&emsp;&emsp;词法分析主要用于滤除注释，组成词素，并将词法单元序列输出至语法分析。项目中采用按字节读入的方式，并增加了一个向前看符号，用于对操作符提取。C-语言的保留字放进了保留字表中，在提取标识符时查表获得。最终生成的词法单元序列保存在数组列表中，输出至语法分析。

### 语法分析


&emsp;&emsp;由`src/team/weird/compiler/cminus/parser`包实现。<br>
&emsp;&emsp;语法分析主要用于对词法分析输出的词法单元序列进行语法规则的分析，验证该串儿是否可由定义的文法生成，并最终生成语法分析树。项目中采用LL(1)文法自顶向下进行语法分析，其过程如下：<br>

* 提取产生式
* 消除左递归
* 提取左公因子
* 计算FIRST集合
* 计算FOLLOW集合
* 计算SECLECT集合
* 构建预测分析表
* 预测分析
* 构建语法分析树

##### &emsp;提取产生式

&emsp;&emsp;从`production.txt`中提取产生式，并将产生式分为非终结符和产生式体，非终结符作为产生式表的`Key`，由于产生式可能对应多种推导规则，产生式体则继续划分为终结符、非终结符以及ε组成的字符串链表组成的链表，并作为产生式表的`Value`。


##### &emsp;消除左递归

&emsp;&emsp;左递归文法是指对某个串α存在推导A->Aα，即为左递归。由于自顶向下分析法无法处理左递归文法，因此需要先消除左递归。
<br>
&emsp;&emsp;左递归文法分为两种，直接左递归和间接左递归。
<br>
&emsp;&emsp;以下为一个C-语言中函数调用传参文法消除**直接左递归**的例子：
>&emsp; *//Before removing left recurtion*<br>
>&emsp;arg-list -> arg-list , expression | expression <br><br>
>&emsp; *//After removing left recurtion*<br>
>&emsp;arg-list -> expression arg-list' <br>
>&emsp;arg-list'-> , expression arg-list' | empty

<br>
&emsp;&emsp;将所有`A->Aα`推导转换为：<br>
>&emsp;A->β A'<br>
>&emsp;A'->α A' | ε`<br>

&emsp;&emsp;即可消除直接左递归。<br>
&emsp;&emsp;另一种左递归文法为**间接左递归**，即为存在`S->Aa A->Sd`这样经过两步或两步以上推导而产生的左递归。<br>
&emsp;&emsp;项目中将间接左递归归约为有向图的强连通分量，故采用了图论中的*Kosaraju*算法来计算强连通分量，经计算发现C-语言文法中不存在间接左递归。

##### &emsp;提取左公因子

&emsp;&emsp;自顶向下分析法最致命的缺陷是无法处理具有二义性的文法，典型的二义性问题是*Dangling-else*问题,即*S->i E t S | i E t S e S | a*文法，因为具有公因子，自顶向下分析时不能确定应该选择哪一条产生式，故出现语法歧义性。
<br>
&emsp;&emsp;一种简单的处理方式是提取左公因子，但是该方法只能处理较简单的情况，如果存在异步二义性，则需要进一步修改文法。
<br>
&emsp;&emsp;提取左公因子是将产生式右部的公因子提取出来，而将公因子之后的符号放在新的产生式中，从而使语法分析在选择对应产生式时可以延后执行，从而避开直接二义性。
<br>
&emsp;&emsp;下面给出一个对C-文法具有二义性的产生式提取公因子从而消除二义性：<br>

>&emsp;*//Before extracting left common factor*<br>
>&emsp; var-declaration -> type-specifier ID ; | type-specifier ID [ NUM ] ;<br>
><br>
>&emsp;*//After extracting left common factor*<br>
>&emsp; var-declaration -> type-specifier ID general-declaration
>&emsp; general_declaration -> ; | [ NUM ] ;

&emsp;&emsp;由于C-文法中存在异步二义性，故直接对文法进行了修改，未实现提取左公因子部分的代码。

##### &emsp;计算FIRST集合


&emsp;&emsp;FIRST集合的定义是：

>&emsp;&emsp;可从α推导得到的串的首符号的集合，其中α是任意的文法符号串。如果ε也在FIRST(α)中，则形如A->cγ中的c也在FIRST(A)中。

&emsp;&emsp;计算FIRST集合的作用是在预测分析时可以仅根据FIRST集合元素来判断针对输入的符号应该选择哪个产生式。<br>
&emsp;&emsp;FIRST(X)集合的求解规则是：
>&emsp;&emsp;1) 如果X是一个终结符，那么FIRST(X) = X。<br>
>&emsp;&emsp;2) 如果X是一个非终结符，且X->Y1Y2...YK是一个产生式，那么如果对于某个i, a在FIRST(Y1)中且ε在所有FIRST(Y1)、FIRST(Y2)、...FIRST(Yi-1)中，就把a加入到FIRST(X)中。<br>
>&emsp;&emsp;3) 如果X -> ε是一个产上式，则将ε加入到FIRST(X)中。

&emsp;&emsp;该项目在求解FIRST集合时，采用了递归的方式，形如`A->B B->b`这样的简单产生式，以A非终结符对应的产生式作为入口，右部首符号为非终结符B，递归求B的FIRST，B的右部首符号为终结符b，加入FIRST(B)，递归返回时再将B的FIRST加入A的FIRST。

##### &emsp;计算FOLLOW集合

&emsp;&emsp;FOLLOW集合的定义是：<br>

>&emsp;&emsp;对于非终结符A，可能在某些句型中紧跟在A右边的终结符号的集合。也就是说，如果存在形如S->αAaβ的推导，终结符号a就在FOLLOW(A)中，其中α和β是文法符号串。

&emsp;&emsp;计算FOLLOW集合的作用是对于某些FIRST集合存在ε的产生式，FOLLOW集合的元素也可作为FIRST集合的一元，帮助语法分析进行选择。<br>

&emsp;&emsp;FOLLOW(A)的求解规则是：

>&emsp;&emsp;1) 将$放到FOLLOW(S)中，其中S是开始符号，而$是输入右端的结束标记。<br>
>&emsp;&emsp;2) 如果存在一个产生式A->αBβ，那么FIRST(β)中除ε以外的所有符号都在FOLLOW(B)
中。<br>
>&emsp;&emsp;3) 如果存在一个产生式A->αB，或存在产生式A->αBβ且FIRST(β)包含ε，那么FOLLOW(A)中的所有符号都在FOLLOW(B)中。

&emsp;&emsp;项目在求解FOLLOW集合时，依旧采用递归的方式，对每个非终结符，扫描所有其他产生式，找到产生式右部含有该非终结符的产生式，将含有该非终结符的右部的非终结符的FIRST集合加入该非终结符FOLLOW中，若为终结符则直接加入。另外对于FIRST含有ε的产生式，则递归加入FOLLOW直到递归无符号时停止。
##### &emsp;计算SECLECT集合
&emsp;&emsp;SELECT集合实则为右部产生式的FIRST集合，故右部产生式首符号若为终结符则直接加入SELECT集合，若为非终结符则判断是否有ε，若无，则直接将首符号FIRST集合加入SELECT，若有，则将首符号的FIRST以及FOLLOW都加入SELECT。
##### &emsp;构建预测分析表
&emsp;&emsp;预测分析表是一个二维数组表，X维度是所有终结符号和$，Y维度是所有非终结符，空白条目表示错误条目，非空白条目将指明应该用该条目中的产生式来拓展相应的非终结符号。<br>
&emsp;&emsp;预测分析表的作用是为后面的预测分析提供查表的方式，由查表来决定下一步该拓展哪个非终结符。此处应该注意，正常的预测分析表应该每个条目只有一个产生式，而绝对不能有两个产生式，如果有两个产生式，通常证明文法是具有二义性的，预测分析时会因为歧义而出错。
##### &emsp;预测分析 & 构建语法分析树
&emsp;&emsp;预测分析是LL(1)文法分析最重要的过程，主要使用栈进行预测分析，项目中按照龙书上给出的伪代码步骤进行了预测分析，并在分析过程中构建语法分析树，预测分析伪代码如下：<br>
<pre>
<code>将开始符号S压栈；
while(!Stack.isEmpty){
	令Symbol = 栈顶符号；
	//token[index]为当前输入符号
	if(Symbol.equals(token[index])){
		Stack.pop()；
		index++;
	}
	else if(Symbol是一个终结符)	
		errorlist.addError();
	else if(PredictiveAnalysisTable[Symbol, a]是空条目)	
		errorlist.addError();
	else {
		//弹栈并将弹出元素构建为语法树节点
		Node node = new Node(Stack.pop())
		Iterator<Production> iter;
		//迭代将弹出元素的产生式加入node的孩子节点中
		while(iter.hasNext){
			node.addChild(new Node(iter.next()));
		}
		将该产生式的所有符号按顺序压栈；
	}
}
</pre></code>

### 抽象语法树构建

##### &emsp;AST结构

&emsp;&emsp;由于C-语言是面向过程语言，因此可看成是`Function_Declaration`与`Global_Variable_Declaration`组成的链表。另外，每个函数中还应该具有`Local_Variable_Declaration`、`Expression`以及`Statement`，故C-语言AST节点结构如下：<br>
<pre>
<code>&emsp;        Declaration
    ---------------------			
&emsp;&emsp;&emsp;|   ArrayDeclaration  |
&emsp;&emsp;&emsp;| FunctionDeclaration |
&emsp;&emsp;&emsp;| VariableDeclaration |
    ---------------------
&emsp;	  Statement	
    ---------------------			
&emsp;&emsp;&emsp;|  CompoundStatement  |
&emsp;&emsp;&emsp;| ExpressionStatement |
&emsp;&emsp;&emsp;|  IterationStatement |
&emsp;&emsp;&emsp;|   ReturnStatement   |
    ---------------------	
&emsp;	 Expression	
    ---------------------			
&emsp;&emsp;&emsp;|   AssignExpression  |
&emsp;&emsp;&emsp;|   BinaryExpression  |
&emsp;&emsp;&emsp;|  LiteralExpression  |
&emsp;&emsp;&emsp;|  VariableExpression |
    ---------------------	
</pre>
</code>
<br>
这样的结构借鉴了*Luke van der Hoeven* 团队实现的编译器构建AST的思想，在此感谢大神。
##### &emsp;AST建立
&emsp;&emsp;由于LL(1)文法输出的是一棵语法分析树，树中含有大量无关节点，譬如epsilon和一些中间转换的非终结符等，故必须将这些无关枝节剪去，从而生成一棵全新的AST，之后的代码生成可直接在AST上进行操作，成为相对独立的过程。<br>
&emsp;&emsp;本项目中采用递归下降的思想，通过拓扑排序的方式对语法树节点挨个遍历，通过函数参数传递综合属性，函数返回值传递继承属性，生成AST。


### 中间代码生成