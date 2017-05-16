# SelectCityDemo
仿美团/大众点评的选择城市界面
界面总共分为两个部分，一个是列表界面，一个是搜索界面，采用两个fragment的hide和show来进行切换。  
- 列表界面上方展示当前定位城市和正在看的城市作为recyclerview的header添加进去的。  
- 右侧A-Z的字母索引的实现在SliderView，里面可以根据自己的需要进行改动。
- 在Activity或者Fragment中需要给每个头部的开始位置指定position，方便在滑动SliderView的时候跳转到正确的位置。

Demo比较简单，具体分析可以参考博客：  
[仿美团/大众点评的选择城市界面](https://mfkauko.github.io/2017/05/SelectCityDemo/)

### 特别鸣谢
[列表头部固定](https://github.com/timehop/sticky-headers-recyclerview)  
[优雅的为recyclerview添加header和footer](http://blog.csdn.net/lmj623565791/article/details/51854533)